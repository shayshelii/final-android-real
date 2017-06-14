package com.example.shaysheli.androaid_final;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;

import com.example.shaysheli.androaid_final.fragments.LoginFragment;
import com.example.shaysheli.androaid_final.fragments.Model.Movie;
import com.example.shaysheli.androaid_final.fragments.MovieDetailFragment;
import com.example.shaysheli.androaid_final.fragments.MovieListFragment;

public class MainActivity extends Activity implements LoginFragment.OnFragmentInteractionListener, MovieListFragment.OnListFragmentInteractionListener, MovieDetailFragment.OnFragmentInteractionListener

{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment listFragment =  LoginFragment.newInstance();
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.add(R.id.main_container, listFragment);
        tran.commit();
    }

    public void onFragmentInteractionChangeFrag(Fragment frag) {
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container, frag);
        tran.commit();
    }

    @Override
    public void onListFragmentInteraction(Movie item) {
        MovieDetailFragment mvDetail = MovieDetailFragment.newInstance(item.id);
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container, mvDetail);
        tran.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
