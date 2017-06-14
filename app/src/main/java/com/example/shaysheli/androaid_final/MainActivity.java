package com.example.shaysheli.androaid_final;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;

import com.example.shaysheli.androaid_final.fragments.LoginFragment;

public class MainActivity extends Activity implements LoginFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment listFragment =  LoginFragment.newInstance();
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.add(R.id.main_container, listFragment);
        tran.commit();
    }

    public void onFragmentInteraction(Uri uri) {

    }
}
