package com.example.shaysheli.androaid_final.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by tomer on 7/29/17.
 */

public class ModelFirebase {
    private static final String MOVIES_KEY = "Movies";
    private FirebaseDatabase database;
    private DatabaseReference movieReference;

    public ModelFirebase() {
        database = FirebaseDatabase.getInstance();
        movieReference = database.getReference(MOVIES_KEY);

    }

    // works with firebase
    interface IGetAllMoviesCallback {
        void onComplete(ArrayList<Movie> movies);
        void onCancel();
    }
    public void getAllMovies(final IGetAllMoviesCallback callback){
        movieReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Movie> movies = new ArrayList<Movie>();

                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Movie mv = snap.getValue(Movie.class);
                    movies.add(mv);
                }

                callback.onComplete(movies);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

    // works with firebase
    public void addMovie(Movie mv){
        movieReference.child(mv.id).setValue(mv);
    }

    interface IGetMovieCallback {
        void onComplete(Movie mv);
        void onCancel();
    }
    public void getMovieByID (String movieID, final IGetMovieCallback callback){
        movieReference.child(movieID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Movie mv = dataSnapshot.getValue(Movie.class);
                callback.onComplete(mv);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

    // works with firebase

    interface IRemoveMovieCallback {
        void onComplete(boolean success);
    }
    public void rmMovie(Movie mv, final IRemoveMovieCallback callback) {
        movieReference.child(mv.id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onComplete(databaseError == null);
            }
        });
    }

    // TODO: 7/29/17 change to firebase
    interface IEditMoveCallback {
        void onComplete(boolean success);
    }
    public void editMovie(Movie mv, final IEditMoveCallback callback){
        movieReference.child(mv.id).setValue(mv, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onComplete(databaseError == null);
            }
        });
    }
}
