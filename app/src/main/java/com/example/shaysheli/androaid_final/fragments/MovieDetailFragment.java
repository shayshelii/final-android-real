package com.example.shaysheli.androaid_final.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.example.shaysheli.androaid_final.Model.User;
import com.example.shaysheli.androaid_final.R;
import com.example.shaysheli.androaid_final.Model.Model;
import com.example.shaysheli.androaid_final.Model.Movie;

public class MovieDetailFragment extends Fragment implements View.OnClickListener{
    private static final String MOVIE_ID = "MOVIE_ID";

    private String MovieID;

    private OnFragmentInteractionListener mListener;
    public static FragmentTransaction tran;

    public TextView movieTitle;
    public TextView movieDescription;
    public RatingBar movieRating;
    public MovieDetailFragment() {  }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param movieID Parameter 1.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String movieID) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString(MOVIE_ID, movieID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("dev", "onCreate");
        Model.instance.getCurrentUser(new Model.IGetCurrentUserCallback() {
            @Override
            public void onComplete(final User currentUser) {
                Model.instance.getMovieByID(getArguments().getString(MOVIE_ID), new Model.IGetMovieCallback() {
                    @Override
                    public void onComplete(Movie mv) {
                        setHasOptionsMenu(currentUser.getId().equals(mv.userId));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MovieID = getArguments().getString(MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("dev", "onCreateView");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        final LinearLayout l = (LinearLayout) view.findViewById(R.id.detail_frag);

        movieRating = (RatingBar) view.findViewById(R.id.details_movie_rating);
        movieTitle = (TextView) view.findViewById(R.id.details_movie_name);
        movieDescription = (TextView) view.findViewById(R.id.details_movie_description);


        Model.instance.getMovieByID(MovieID, new Model.IGetMovieCallback() {
            @Override
            public void onComplete(Movie mv) {
                movieTitle.setText(mv.name);
                movieDescription.setText(mv.description);
                movieRating.setRating(Float.parseFloat(mv.rate));

                Model.instance.getImage(mv.imageUrl, new Model.IGetImageCallback() {
                    @Override
                    public void onComplete(Bitmap image) {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), image);
                        l.setBackground(bitmapDrawable);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }

            @Override
            public void onCancel() {

            }
        });

        return view;
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
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        mListener.onFragmentInteraction(MovieID);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(String mvId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                mListener.onFragmentInteraction(MovieID);

                break;
            case android.R.id.home:
                MovieListFragment listFragment = MovieListFragment.newInstance(1, MovieListFragment.adminOptions);
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, listFragment);
                tran.commit();
                break;
            default:
                break;
        }

        return true;
    }
}
