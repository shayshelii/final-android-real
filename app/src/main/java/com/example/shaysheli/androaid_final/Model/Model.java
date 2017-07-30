package com.example.shaysheli.androaid_final.Model;

import com.example.shaysheli.androaid_final.fragments.MovieListFragment;
import com.example.shaysheli.androaid_final.fragments.MymovieRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ShaySheli on 09/06/2017.
 */

public class Model {
    public final static Model instance = new Model();
    private ModelFirebase modelFirebase;

    private Model(){
        modelFirebase = new ModelFirebase();
    }

    // works with firebase
    public void addMovie(Movie mv){
        modelFirebase.addMovie(mv);
    }

    // works with firebase
    public interface IGetAllMoviesCallback {
        void onComplete(ArrayList<Movie> movies);
        void onCancel();
    }
    public void getAllMovies(final IGetAllMoviesCallback callback){
        modelFirebase.getAllMovies(new ModelFirebase.IGetAllMoviesCallback() {
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
        modelFirebase.getMovieByID(movieID, new ModelFirebase.IGetMovieCallback() {
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
        modelFirebase.rmMovie(mv, new ModelFirebase.IRemoveMovieCallback() {
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
                    modelFirebase.editMovie(mv, new ModelFirebase.IEditMoveCallback() {
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
}
