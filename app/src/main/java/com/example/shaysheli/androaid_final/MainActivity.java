package com.example.shaysheli.androaid_final;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
                                                               UserManagementFragment.OnListFragmentInteractionListener,
                                                               MovieListFragment.onListToLoginFragmentInteractionListener
{
    LoginFragment loginFragmentInstance;
    public static MovieListFragment movieListFragmentInstance;
    public static MovieDetailFragment movieDetailFragmentInstance;

    private static Context context;
    static final int REQUEST_WRITE_STORAGE = 11;
    public static FragmentTransaction tran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        }
        if (savedInstanceState == null)
        {
            this.loginFragmentInstance =  LoginFragment.newInstance();
            tran = getFragmentManager().beginTransaction();
            tran.add(R.id.main_container, this.loginFragmentInstance);
            tran.commit();
        }
    }


    public static Context getMyContext(){
        return context;
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
        tran.replace(R.id.main_container, this.movieDetailFragmentInstance);
        tran.commit();
    }

    @Override
    public void onFragmentInteraction(String mvId) {
        AddOrEditFragment details = AddOrEditFragment.newInstance(mvId, "Edit");

        tran = getFragmentManager().beginTransaction();
        tran.hide(this.movieDetailFragmentInstance);
        tran.addToBackStack("backToDetail");
        tran.replace(R.id.main_container, details).commit();
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
        tran.replace(R.id.main_container, frag);
        tran.commit();
    }

    @Override
    public void onUserListFragmentInteraction(User item){

    }

    @Override
    public void onListToLoginFragmentInteraction() {
        tran = getFragmentManager().beginTransaction();
        tran.add(R.id.main_container, loginFragmentInstance).commit();
    }
}
