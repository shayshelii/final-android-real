package com.example.shaysheli.androaid_final.Model;

import com.example.shaysheli.androaid_final.fragments.MymovieRecyclerViewAdapter;

import java.util.ArrayList;

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


    public void rmMovies() {
        for (Movie mv : MymovieRecyclerViewAdapter.checkedMovieToDel.values()) {
            Model.instance.rmMovie(mv, new Model.IRemoveMovie() {
                @Override
                public void onComplete(boolean success) {

                }
            });
        }
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

    public interface IGetCurrentUserCallback {
        void onComplete(User currentUser);
    }
    public void getCurrentUser(final IGetCurrentUserCallback callback) {
        modelUserFirebase.getCurrentUser(new ModelUserFirebase.IGetCurrentUserCallback() {
            @Override
            public void onComplete(User user) {
                callback.onComplete(user);
            }
        });
    }

    public interface IAddUser {
        void onComplete(User user);
        void onError(String reason);
    }
    public void addUser(User user, String password, final IAddUser callback ) {
        modelUserFirebase.addUser(user, password, new ModelUserFirebase.IAddUser() {
            @Override
            public void onComplete(User user) {
                callback.onComplete(user);
            }

            @Override
            public void onError(String reason) {
                callback.onError(reason);
            }
        });
    }

    public interface IGetUserByIdCallback {
        void onComplete(User user);
        void onCancel();
    }
    public void getUserById(String id, final IGetUserByIdCallback callback) {
        modelUserFirebase.getUserById(id, new ModelUserFirebase.IGetUserByIdCallback() {
            @Override
            public void onComplete(User user) {
                callback.onComplete(user);
            }

            @Override
            public void onCancel() {
                callback.onCancel();
            }
        });
    }

    public interface IGetUserLoginCallback {
        void onComplete(User user);
    }
    public void userLogin(String email, String password , final IGetUserLoginCallback callback) {
        modelUserFirebase.userLogin(email, password, new ModelUserFirebase.IGetUserLoginCallback() {
            @Override
            public void onComplete(User user) {
                callback.onComplete(user);
            }
        });
    }

    public interface IGetAllUsersCallback{
        void onComplete(ArrayList<User> users);
        void onCancel();
    }
    public void getAllUsers(final IGetAllUsersCallback callback) {
        modelUserFirebase.getAllUsers(new ModelUserFirebase.IGetAllUsersCallback() {
            @Override
            public void onComplete(ArrayList<User> users) {
                callback.onComplete(users);
            }

            @Override
            public void onCancel() {
                callback.onCancel();
            }
        });
    }

    public interface IUpdateUserCallback {
        void onComplete(boolean success);
    }
    public void updateUser(User user, final IUpdateUserCallback callback) {
        modelUserFirebase.updateUser(user, new ModelUserFirebase.IGetUpdateUserCallback() {
            @Override
            public void onComplete(boolean success) {
                callback.onComplete(success);
            }
        });
    }
}