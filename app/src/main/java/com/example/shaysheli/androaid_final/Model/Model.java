package com.example.shaysheli.androaid_final.Model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ShaySheli on 09/06/2017.
 */

public class Model {
    public final static Model instance = new Model();
    private static int id = 1;
    private Model(){
        Calendar c = Calendar.getInstance();

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

    public void addMovie(Movie mv){
        mv.id = ++id + "";
        mv.imageUrl = "../res/drawable/grid.png";
        data.add(Integer.parseInt(mv.id), mv);
    }

    public ArrayList<Movie> getAllMovies(){
        return data;
    }

    public Movie getMovieByID (String movieID){
        for (Movie movie: data) {
            if (movie.id.equals(movieID))
                return movie;
        }

        return null;
    }

    public Boolean rmMovie(Movie mv) {
        id--;
        return data.remove(mv);
    }

    public Boolean editMovie(Movie mv){
        if (this.getMovieByID(mv.id) == null) {
            this.addMovie(mv);
        }else {
            data.set(Integer.parseInt(mv.id), mv);
        }

        return true;
    }
}
