package com.example.shaysheli.androaid_final.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaysheli.androaid_final.Model.User;
import com.example.shaysheli.androaid_final.R;
import com.example.shaysheli.androaid_final.Model.Model;
import com.example.shaysheli.androaid_final.Model.Movie;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MovieListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public static FragmentTransaction tran;
    public static Boolean adminOptions;
    public static ArrayList<Movie> myUserMovies = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieListFragment() {
    }

    public static MovieListFragment newInstance(int columnCount, Boolean adminOptions) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        MovieListFragment.adminOptions = adminOptions;
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            if (myUserMovies != null){
                recyclerView.setAdapter(new MymovieRecyclerViewAdapter(myUserMovies, mListener));
            } else
                Model.instance.getAllMovies(new Model.IGetAllMoviesCallback() {
                    @Override
                    public void onComplete(ArrayList<Movie> movies) {
                        recyclerView.setAdapter(new MymovieRecyclerViewAdapter(movies, mListener));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Movie item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (MovieListFragment.adminOptions)
            inflater.inflate(R.menu.menu_main_admin, menu);
        else
            inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new:
                AddOrEditFragment details = AddOrEditFragment.newInstance(null, "Add");
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, details).commit();

                break;
            case R.id.my_movies:
                Model.instance.getCurrentUser(new Model.IGetCurrentUserCallback() {
                    @Override
                    public void onComplete(User currentUser) {
                        changeViewByUserId(currentUser.id);
                    }
                });

                break;
            case R.id.del_movies:
                Model.instance.rmMovies();
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

    public void changeViewByUserId(final String userId) {

        Model.instance.getAllMovies(new Model.IGetAllMoviesCallback() {
            @Override
            public void onComplete(ArrayList<Movie> movies) {
                for (Movie mv : movies) {
                    if (!mv.userId.equals(userId)) {
                        movies.remove(mv);
                    }
                }

                myUserMovies = movies;
            }
            @Override
            public void onCancel() {
            }
        });
    }
}
