package com.example.shaysheli.androaid_final.Model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShaySheli on 09/06/2017.
 */

public class Model {
    public final static Model instance = new Model();
    private ModelMovieFirebase modelMovieFirebase;
    private ModelUserFirebase modelUserFirebase;

    private Model(){
        modelMovieFirebase = new ModelMovieFirebase();
        modelUserFirebase = new ModelUserFirebase();
    }

    // works with firebase
    public void addMovie(Movie mv){
        modelMovieFirebase.addMovie(mv);
    }

    // works with firebase
    public interface IGetAllMoviesCallback {
        void onComplete(ArrayList<Movie> movies);
        void onCancel();
    }
    public void getAllMovies(final IGetAllMoviesCallback callback){
        modelMovieFirebase.getAllMovies(new ModelMovieFirebase.IGetAllMoviesCallback() {
            @Override
            public void onComplete(ArrayList<Movie> movies) {
                callback.onComplete(movies);
            }

            @Override
            public void onCancel() {
                callback.onCancel();
            }
        });
    }

    // works with firebase
    public interface IGetMovieCallback {
        void onComplete(Movie mv);
        void onCancel();
    }// works with firebase
    public void getMovieByID (String movieID, final IGetMovieCallback callback){
        modelMovieFirebase.getMovieByID(movieID, new ModelMovieFirebase.IGetMovieCallback() {
            @Override
            public void onComplete(Movie mv) {
                callback.onComplete(mv);
            }

            @Override
            public void onCancel() {
                callback.onCancel();
            }
        });
    }

     // working with firebase
    public interface IRemoveMovie {
         void onComplete(boolean success);
     }
    public void rmMovie(Movie mv, final IRemoveMovie callback) {
        modelMovieFirebase.rmMovie(mv, new ModelMovieFirebase.IRemoveMovieCallback() {
            @Override
            public void onComplete(boolean success) {
                callback.onComplete(success);
            }
        });
    }


    // works with firebase
    public interface IGetMovieEditSuccessCallback {
        void onComplete(boolean success);
    }
    public void editMovie(final Movie mv, final IGetMovieEditSuccessCallback callback){

        this.getMovieByID(mv.id, new IGetMovieCallback() {
            @Override
            public void onComplete(Movie resultMv) {
                if (resultMv == null) {
                    addMovie(mv);
                    callback.onComplete(true);
                }
                else {
                    modelMovieFirebase.editMovie(mv, new ModelMovieFirebase.IEditMoveCallback() {
                        @Override
                        public void onComplete(boolean success) {
                            callback.onComplete(success);
                        }
                    });
                }
            }

            @Override
            public void onCancel() {
                callback.onComplete(false);
            }
        });
    }

    // TODO: 7/30/17 users options!

    public User getCurrentUser() {
        User user = modelUserFirebase.getCurrentUser();

        return user;
    }

    public interface IGetUser {
        void onComplete(User user);
    }
    public void addUser(User user, String passwod, final IGetUser callback ) {
        modelUserFirebase.addUser(user, passwod, new ModelUserFirebase.IAddUser() {
            @Override
            public void onComplete(User user) {
                callback.onComplete(user);
            }
        });
    }
}