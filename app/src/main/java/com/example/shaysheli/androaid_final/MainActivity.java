package com.example.shaysheli.androaid_final;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.shaysheli.androaid_final.fragments.AddOrEditFragment;
import com.example.shaysheli.androaid_final.fragments.LoginFragment;
import com.example.shaysheli.androaid_final.Model.Movie;
import com.example.shaysheli.androaid_final.fragments.MovieDetailFragment;
import com.example.shaysheli.androaid_final.fragments.MovieListFragment;
import com.example.shaysheli.androaid_final.fragments.RegisterFragment;

public class MainActivity extends ActionBarActivity implements LoginFragment.OnFragmentInteractionListener,
                                                               MovieListFragment.OnListFragmentInteractionListener,
                                                               MovieDetailFragment.OnFragmentInteractionListener,
                                                               AddOrEditFragment.OnFragmentInteractionListener,
                                                               RegisterFragment.OnFragmentInteractionListener
{
    MovieListFragment movieListFragmentInstance;
    MovieDetailFragment movieDetailFragmentInstance;

    public static FragmentTransaction tran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoginFragment listFragment =  LoginFragment.newInstance();
        tran = getFragmentManager().beginTransaction();
        tran.add(R.id.main_container, listFragment);
        tran.commit();
    }

    public void onFragmentInteractionChangeFrag(Fragment frag) {
        if (frag instanceof MovieListFragment)
            this.movieListFragmentInstance = (MovieListFragment) frag;
        tran = getFragmentManager().beginTransaction();
        tran.addToBackStack("backLogin");
        tran.replace(R.id.main_container, frag);
        tran.commit();
    }

    @Override
    public void onListFragmentInteraction(Movie item) {
        MovieDetailFragment mvDetail = MovieDetailFragment.newInstance(item.id);
        this.movieDetailFragmentInstance = mvDetail;

        tran = getFragmentManager().beginTransaction();
        tran.hide(this.movieListFragmentInstance);
        tran.addToBackStack("backToList");
        tran.add(R.id.main_container, mvDetail);
        tran.commit();
    }

    @Override
    public void onFragmentInteraction(String mvId) {
        AddOrEditFragment details = AddOrEditFragment.newInstance(mvId, "Edit");

        tran.hide(this.movieDetailFragmentInstance);
        tran.addToBackStack("backToDetail");
        tran = getFragmentManager().beginTransaction();
        tran.add(R.id.main_container, details).commit();
    }

    @Override
    public void onFragmentInteractionAddOrEdit() {
        MovieListFragment listFragment = MovieListFragment.newInstance(1);
        tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container, listFragment);
        tran.commit();
    }

    @Override
    public void onFragmentRegisterInteraction() {
    }
}
