package com.example.shaysheli.androaid_final.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by tomer on 7/30/17.
 */

public class ModelUserFirebase {
    private static final String USERS_KEY = "Users";
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference usersReference;

    private User currentUser;

    public ModelUserFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference(USERS_KEY);
    }


    interface IGetCurrentUserCallback {
        void onComplete(User user);
    }
    public void getCurrentUser(final IGetCurrentUserCallback callback) {
        // singleton
        if (currentUser != null) {
            callback.onComplete(currentUser);
        }
        else {
            final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (firebaseUser == null) {
                callback.onComplete(null);
            }
            else {
                getUserById(firebaseUser.getUid(), new IGetUserById() {
                    @Override
                    public void onComplete(User user) {
                        currentUser = new User(user);
                        currentUser.setId(firebaseUser.getUid());
                        callback.onComplete(currentUser);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        }
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

                        if (firebaseUser != null) {
                            usersReference.child(firebaseUser.getUid()).setValue(user);
                            callback.onComplete(user);
                        }
                        else {
                            callback.onComplete(null);
                        }
                    }
                });
    }

    interface IGetUserById {
        void onComplete(User user);
        void onCancel();
    }
    public void getUserById(String id, final IGetUserById callback) {
        usersReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onComplete(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

    interface IGetUserLoginCallback {
        void onComplete(User user);
    }
    public void userLogin(User user, String password , final IGetUserLoginCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                getCurrentUser(new IGetCurrentUserCallback() {
                    @Override
                    public void onComplete(User user) {
                        callback.onComplete(user);
                    }
                });
            }
        });
    }
}