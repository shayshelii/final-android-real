package com.example.shaysheli.androaid_final.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by tomer on 7/30/17.
 */

public class ModelUserFirebase {
    private FirebaseAuth firebaseAuth;

    public ModelUserFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public User getCurrentUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        User modelUser = new User(user.getDisplayName(), user.getEmail(), false);
        modelUser.id = user.getUid();

        return modelUser;
    }

    interface IAddUser {
        void onComplete(User user);
    }
    public void addUser(final User user, String password, final IAddUser callback) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser firebaseUser = task.isSuccessful() ?
                                firebaseAuth.getCurrentUser() : null;

                        if (firebaseUser  != null) {
                            updateUserProfile(firebaseUser, user, callback);
                        }
                        else {
                            callback.onComplete(null);
                        }
                    }
                });
    }

    private void updateUserProfile(FirebaseUser firebaseUser, final User user, final IAddUser callback) {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getName())
                .build();

        firebaseUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.onComplete(user);
            }
        });
    }


}
