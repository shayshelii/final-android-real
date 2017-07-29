package com.example.shaysheli.androaid_final.Model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ShaySheli on 09/06/2017.
 */

public class Model {
    public final static Model instance = new Model();
    private static int id = 1;
    private ModelFirebase modelFirebase;

    private Model(){
        Calendar c = Calendar.getInstance();

        modelFirebase = new ModelFirebase();

        for(int i = 0; i < 5; i++) {
            Movie mv = new Movie();
            mv.name = "Harry Potter " + i;
            mv.id = id + "";
            mv.rate = i + "";
            mv.dateCreated = c.getTime().toString();
            data.add(i, mv);
            id++;
        }
    }

    private ArrayList<Movie> data = new ArrayList<>();

    // works with firebase
    public void addMovie(Movie mv){
        mv.id = ++id + "";
        mv.imageUrl = "../res/drawable/grid.png";

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

    // // TODO: 7/29/17 change to firebase
    public Boolean rmMovie(Movie mv) {
        id--;
        return data.remove(mv);
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
                }
                else {

                    // TODO: 7/29/17 change to firebase!!
                    data.set(Integer.parseInt(mv.id), mv);
                }

                callback.onComplete(true);
            }

            @Override
            public void onCancel() {
                callback.onComplete(false);
            }
        });
    }
}
