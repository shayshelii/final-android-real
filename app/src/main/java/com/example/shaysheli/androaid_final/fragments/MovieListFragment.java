package com.example.shaysheli.androaid_final.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaysheli.androaid_final.MainActivity;
import com.example.shaysheli.androaid_final.Model.User;
import com.example.shaysheli.androaid_final.R;
import com.example.shaysheli.androaid_final.Model.Model;
import com.example.shaysheli.androaid_final.Model.Movie;

import java.util.ArrayList;
import java.util.List;

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
    private onListToLoginFragmentInteractionListener mLoginListener;
    public static FragmentTransaction tran;
    public static Boolean adminOptions;
    public RecyclerView recyclerView;
    public static List<Movie> mvMyList;
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
        if (savedInstanceState == null) { getFragmentManager().beginTransaction().replace(R.id.main_container, MainActivity.movieListFragmentInstance);
        }
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
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            if (MovieListFragment.mvMyList != null) {
                recyclerView.setAdapter(new MymovieRecyclerViewAdapter(MovieListFragment.mvMyList, mListener));
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

        if (context instanceof onListToLoginFragmentInteractionListener) {
            mLoginListener = (onListToLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onListToLoginFragmentInteractionListener");
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
        void onListFragmentInteraction(Movie item);
    }

    public interface onListToLoginFragmentInteractionListener {
        void onListToLoginFragmentInteraction();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (MovieListFragment.adminOptions)
            inflater.inflate(R.menu.menu_main_admin, menu);
        else if (MovieListFragment.mvMyList != null)
            inflater.inflate(R.menu.menu_main_all_movies, menu);
        else
            inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new:
                AddOrEditFragment details = AddOrEditFragment.newInstance(null, "Add");
                tran = getFragmentManager().beginTransaction();
                tran.addToBackStack("");
                tran.add(R.id.main_container, details).commit();

                break;
            case R.id.sign_out:
                Model.instance.signOut();
                mLoginListener.onListToLoginFragmentInteraction();

                break;
            case R.id.admin_panel:
                AdminPanelFragment adminPanelFragment = AdminPanelFragment.newInstance();
                tran = getFragmentManager().beginTransaction();
                tran.addToBackStack("");
                tran.add(R.id.main_container, adminPanelFragment).commit();

                break;
            case R.id.all_movies:
                MovieListFragment.mvMyList = null;
                (recyclerView.getAdapter()).notifyDataSetChanged();

                MovieListFragment listFragments = MovieListFragment.newInstance(1, MovieListFragment.adminOptions);
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, listFragments);
                tran.commit();

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
                ArrayList<Movie> movieToDel = new ArrayList<>();
                for (int i = 0; i < movies.size(); i++) {
                    if (!movies.get(i).userId.equals(userId)) {
                        movieToDel.add(movies.get(i));
                    }
                }

                for (Movie mv :
                        movieToDel) {
                    movies.remove(mv);
                }

                MovieListFragment.mvMyList = movies;
                (recyclerView.getAdapter()).notifyDataSetChanged();

                MovieListFragment listFragment = MovieListFragment.newInstance(1, MovieListFragment.adminOptions);
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, listFragment);
                tran.commit();
            }
            @Override
            public void onCancel() {
            }
        });
    }
}
