package com.example.shaysheli.androaid_final.fragments.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ShaySheli on 09/06/2017.
 */

public class Model {
    public final static Model instance = new Model();
    private static int id = 5;
    private Model(){
        Calendar c = Calendar.getInstance();

        for(int i = 1; i <= 5; i++) {
            Movie mv = new Movie();
            mv.name = "kuku" + i;
            mv.id = i + "";
            mv.rate = i;
            mv.dateCreated = c.getTime();
            data.add(i, mv);
        }
    }

    private ArrayList<Movie> data = new ArrayList<>();

    public ArrayList<Movie> getAllStudents(){
        return data;
    }
}
