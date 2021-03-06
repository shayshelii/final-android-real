package com.example.shaysheli.androaid_final.fragments;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shaysheli.androaid_final.Model.Model;
import com.example.shaysheli.androaid_final.R;
import com.example.shaysheli.androaid_final.Model.Movie;
import com.example.shaysheli.androaid_final.fragments.MovieListFragment.OnListFragmentInteractionListener;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Movie} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MymovieRecyclerViewAdapter extends RecyclerView.Adapter<MymovieRecyclerViewAdapter.ViewHolder> {

    public List<Movie> mValues;
    private final OnListFragmentInteractionListener mListener;
    public static Hashtable<String, Movie> checkedMovieToDel = new Hashtable<>();

    public MymovieRecyclerViewAdapter(List<Movie> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Movie wantedMovie =  mValues.get(position);

        holder.mItem = wantedMovie;
        holder.mIdView.setText(wantedMovie.id);
        holder.mContentView.setText(wantedMovie.name);
        holder.mRaiting.setRating(Float.parseFloat(wantedMovie.rate));
        holder.mImage.setTag(holder.mItem.imageUrl);
        if (MovieListFragment.adminOptions && MovieListFragment.showCB) {
            holder.mCB.setVisibility(View.VISIBLE);
            holder.mCB.setChecked(wantedMovie.checked);
        }

        if (holder.mItem.imageUrl != null && !holder.mItem.imageUrl.isEmpty() && !holder.mItem.imageUrl.equals("")) {
            holder.mProgressBar.setVisibility(View.VISIBLE);
            Model.instance.getImage(wantedMovie.imageUrl, new Model.IGetImageCallback() {
                @Override
                public void onComplete(Bitmap image) {
                    String tagUrl = holder.mImage.getTag().toString();
                    if (tagUrl.equals(holder.mItem.imageUrl)) {
                        holder.mImage.setImageBitmap(image);
                        holder.mProgressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancel() {
                    holder.mProgressBar.setVisibility(View.GONE);
                }
            });
        }

        holder.mCB.setTag(position);
        holder.mCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = v.getTag().toString();
                Movie mv = MymovieRecyclerViewAdapter.checkedMovieToDel.get(pos);
                if (mv != null)
                    checkedMovieToDel.remove(pos);
                else {
                    checkedMovieToDel.put(pos, wantedMovie);
                    wantedMovie.checked = !wantedMovie.checked;
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final RatingBar mRaiting;
        public final CheckBox mCB;
        public final ImageView mImage;
        public Movie mItem;
        public final ProgressBar mProgressBar;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.strow_id);
            mContentView = (TextView) view.findViewById(R.id.strow_name);
            mRaiting = (RatingBar) view.findViewById(R.id.strow_rating);
            mCB = (CheckBox) view.findViewById(R.id.strow_cb);
            mImage = (ImageView) view.findViewById(R.id.strow_image);
            mProgressBar = (ProgressBar) view.findViewById(R.id.strow_progressBar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
