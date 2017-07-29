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
    public FirebaseDatabase database;

    public ModelFirebase() {
        database = FirebaseDatabase.getInstance();
    }

    // works with firebase
    interface IGetAllMoviesCallback {
        void onComplete(ArrayList<Movie> movies);
        void onCancel();
    }
    public void getAllMovies(final IGetAllMoviesCallback callback){
        DatabaseReference reference = database.getReference("Movies");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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
        DatabaseReference reference = database.getReference("Movies");
        reference.child(mv.id).setValue(mv);
    }

    interface IGetMovieCallback {
        void onComplete(Movie mv);
        void onCancel();
    }

    public void getMovieByID (String movieID, final IGetMovieCallback callback){
        DatabaseReference reference = database.getReference("Movies");
        reference.child(movieID).addListenerForSingleValueEvent(new ValueEventListener() {
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

    // TODO: 7/29/17 change to firebase
    public Boolean rmMovie(Movie mv) {
        return false;
    }

    // TODO: 7/29/17 change to firebase
    public Boolean editMovie(Movie mv){
        return true;
    }
}
