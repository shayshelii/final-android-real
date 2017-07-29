package com.example.shaysheli.androaid_final.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.shaysheli.androaid_final.Model.Model;
import com.example.shaysheli.androaid_final.Model.Movie;
import com.example.shaysheli.androaid_final.Model.MyDatePicker;
import com.example.shaysheli.androaid_final.R;

import static com.example.shaysheli.androaid_final.fragments.MovieDetailFragment.tran;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddOrEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddOrEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOrEditFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MOVIEID = "MOVIEID";
    private static final String ARG_TYPE = "TYPE";

    // TODO: Rename and change types of parameters
    private String MOVIEID;
    private String TYPE;
    private static Button btnAddEdit = null;
    private static Movie mvEdit;
    private static EditText edtName = null;
    private static EditText edtId = null;
    private static RatingBar edtRate = null;
    private static ImageView edtImage = null;
    private static MyDatePicker datePicker = null;

    private OnFragmentInteractionListener mListener;

    public AddOrEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param MOVIEID Parameter 1.
     * @param TYPE Parameter 2.
     * @return A new instance of fragment AddOrEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOrEditFragment newInstance(String MOVIEID, String TYPE) {
        AddOrEditFragment fragment = new AddOrEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIEID, MOVIEID);
        args.putString(ARG_TYPE, TYPE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MOVIEID = getArguments().getString(ARG_MOVIEID);
            TYPE = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_or_edit, container, false);
        btnAddEdit = (Button) v.findViewById(R.id.AddEditButton);
        Button btnAddEditCancel = (Button) v.findViewById(R.id.AddEditButtonCancel);
        Button btnAddEditDel = (Button) v.findViewById(R.id.AddEditButtonDel);
        edtName = (EditText) v.findViewById(R.id.AddEditName);
        edtId = (EditText) v.findViewById(R.id.AddEditId);
        edtRate = (RatingBar) v.findViewById(R.id.AddEditRating);
        edtRate.setIsIndicator(false);
        edtImage = (ImageView) v.findViewById(R.id.AddEditImage);
        datePicker = (MyDatePicker) v.findViewById(R.id.datePicker);

        if (TYPE.equals("Add")) {
            btnAddEdit.setText("Add");
            mvEdit = new Movie();
        }else {
            btnAddEdit.setText("Edit");
            btnAddEditDel.setVisibility(View.VISIBLE);
            Model.instance.getMovieByID(MOVIEID, new Model.IGetMovieCallback() {
                @Override
                public void onComplete(Movie mv) {
                    mvEdit = mv;
                    edtId.setText(mvEdit.id);
                    edtName.setText(mvEdit.name);
                    edtRate.setRating(Float.parseFloat(mvEdit.rate));
                    datePicker.setText(mvEdit.dateCreated);
                }

        }

                @Override
                public void onCancel() {

                }
            });
        }

        btnAddEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Model.instance.rmMovie(mvEdit, new Model.IRemoveMovie() {
                    @Override
                    public void onComplete(boolean success) {
                        if (success) {
                            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                            alertDialog.setTitle("STUDENT DELETED");
                            alertDialog.setMessage("SUCCESS");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }

                        mListener.onFragmentInteractionAddOrEdit();
                    }
                });
            }
        });

        btnAddEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteractionAddOrEdit();
            }
        });

        btnAddEdit.setOnClickListener(this);

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
        mListener = null;
    }

    @Override
    public void onClick(final View v) {
        mvEdit.name = edtName.getText().toString();
        final String idToCheck = edtId.getText().toString();
        mvEdit.rate = edtRate.getRating() + "";
        mvEdit.imageUrl = "../res/drawable/grid.png";
        mvEdit.dateCreated = datePicker.getText().toString();

        Model.instance.getMovieByID(idToCheck, new Model.IGetMovieCallback() {
            @Override
            public void onComplete(Movie mv) {
                alertUserAboutAddOrUpdate(v, mv, idToCheck);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void alertUserAboutAddOrUpdate(final View v, Movie mv, String idToCheck) {
        if (((mv != null) && (btnAddEdit.getText().equals("Add"))) ||
                ((!idToCheck.equals(mvEdit.id)) && mv != null) && (btnAddEdit.getText().equals("Edit")))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
            alertDialog.setTitle("ID IN USE");
            alertDialog.setMessage("Choose another id");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        else {
            mvEdit.id = idToCheck;
            Model.instance.editMovie(mvEdit, new Model.IGetMovieEditSuccessCallback() {
                @Override
                public void onComplete(boolean success) {
                    if (success) {
                        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                        alertDialog.setTitle("STUDENT SAVED");
                        alertDialog.setMessage("SUCCESS");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }

                    mListener.onFragmentInteractionAddOrEdit();
                }
            });
        }
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
        void onFragmentInteractionAddOrEdit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                switch (TYPE) {
                    // return to list mode
                    case "Add":
                        MovieListFragment listFragment = MovieListFragment.newInstance(1);
                        tran = getFragmentManager().beginTransaction();
                        tran.replace(R.id.main_container, listFragment);
                        tran.commit();
                        break;

                    // return to view mode
                    case "Edit":
                        MovieDetailFragment details = MovieDetailFragment.newInstance(MOVIEID);
                        tran = getFragmentManager().beginTransaction();
                        tran.replace(R.id.main_container, details).commit();
                        break;
                }
                break;
            default:
                break;
        }

        return true;
    }
}
