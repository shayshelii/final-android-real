package com.example.shaysheli.androaid_final;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.shaysheli.androaid_final.Model.User;
import com.example.shaysheli.androaid_final.fragments.AddOrEditFragment;
import com.example.shaysheli.androaid_final.fragments.AdminPanelFragment;
import com.example.shaysheli.androaid_final.fragments.LoginFragment;
import com.example.shaysheli.androaid_final.Model.Movie;
import com.example.shaysheli.androaid_final.fragments.MovieDetailFragment;
import com.example.shaysheli.androaid_final.fragments.MovieListFragment;
import com.example.shaysheli.androaid_final.fragments.RegisterFragment;
import com.example.shaysheli.androaid_final.fragments.UserManagementFragment;

public class MainActivity extends ActionBarActivity implements LoginFragment.OnFragmentInteractionListener,
                                                               MovieListFragment.OnListFragmentInteractionListener,
                                                               MovieDetailFragment.OnFragmentInteractionListener,
                                                               AddOrEditFragment.OnFragmentInteractionListener,
                                                               RegisterFragment.OnFragmentInteractionListener,
                                                               AdminPanelFragment.OnFragmentInteractionListener,
                                                               UserManagementFragment.OnListFragmentInteractionListener
{
    LoginFragment loginFragmentInstance;
    public static MovieListFragment movieListFragmentInstance;
    MovieDetailFragment movieDetailFragmentInstance;

    public static FragmentTransaction tran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null)
        {
            this.loginFragmentInstance =  LoginFragment.newInstance();
            tran = getFragmentManager().beginTransaction();
            tran.add(R.id.main_container, this.loginFragmentInstance);
            tran.commit();
        }
    }

    public void onFragmentInteractionChangeFrag(Fragment frag) {
        tran = getFragmentManager().beginTransaction();

        if (frag instanceof MovieListFragment) {
            this.movieListFragmentInstance = (MovieListFragment) frag;
            tran.replace(R.id.main_container, frag);
        } else {
            tran.addToBackStack("backToLogin");
            tran.add(R.id.main_container, frag);
        }

        tran.commit();
    }

    @Override
    public void onListFragmentInteraction(Movie item) {
        this.movieDetailFragmentInstance = MovieDetailFragment.newInstance(item.id);

        tran = getFragmentManager().beginTransaction();
        tran.hide(this.movieListFragmentInstance);

        tran.addToBackStack("backList");
        tran.add(R.id.main_container, this.movieDetailFragmentInstance);
        tran.commit();
    }

    @Override
    public void onFragmentInteraction(String mvId) {
        AddOrEditFragment details = AddOrEditFragment.newInstance(mvId, "Edit");

        tran = getFragmentManager().beginTransaction();
        tran.hide(this.movieDetailFragmentInstance);
        tran.addToBackStack("backToDetail");
        tran.add(R.id.main_container, details).commit();
    }

    @Override
    public void onFragmentInteractionAddOrEdit() {
        MovieListFragment listFragment = MovieListFragment.newInstance(1, false);
        tran = getFragmentManager().beginTransaction();
        tran.addToBackStack("backToListOrDetail");
        tran.replace(R.id.main_container, listFragment);
        tran.commit();
    }

    @Override
    public void onFragmentAdminChooseInteraction(Fragment frag) {
        tran = getFragmentManager().beginTransaction();
        tran.addToBackStack("backToAdminPanel");
        tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container, frag);
        tran.commit();
    }

    @Override
    public void onUserListFragmentInteraction(User item){

    }
}
