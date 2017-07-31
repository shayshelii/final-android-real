package com.example.shaysheli.androaid_final.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shaysheli.androaid_final.Model.Movie;
import com.example.shaysheli.androaid_final.R;

public class AdminPanelFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public AdminPanelFragment() {
        // Required empty public constructor
    }

    public static AdminPanelFragment newInstance() {
        AdminPanelFragment fragment = new AdminPanelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin_panel, container, false);
        Button userBtn = (Button) v.findViewById(R.id.admin_user_btn);
        Button moviesBtn = (Button) v.findViewById(R.id.admin_movies_btn);

        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    UserManagementFragment frag = new UserManagementFragment();
                    mListener.onFragmentAdminChooseInteraction(frag);
                }
            }
        });

        moviesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    MovieListFragment listFragment =  MovieListFragment.newInstance(1, true);
                    mListener.onFragmentAdminChooseInteraction(listFragment);
                    MovieListFragment.showCB = true;
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MovieListFragment.showCB = false;
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentAdminChooseInteraction(Fragment frag);
    }
}
