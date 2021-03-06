package com.example.shaysheli.androaid_final.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shaysheli.androaid_final.Model.Model;
import com.example.shaysheli.androaid_final.Model.User;
import com.example.shaysheli.androaid_final.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private EditText emailEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;

    public LoginFragment() { // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.login_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        // TODO: 7/31/17 USE SIGNOUT METHOD IF YOU WANT TO SIGNOUT
        // Model.instance.signOut();

        Model.instance.getCurrentUser(new Model.IGetCurrentUserCallback() {
            @Override
            public void onComplete(User currentUser) {
                if (currentUser == null) {
                    actuallyCreateTheView(view);
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    MovieListFragment listFragment = MovieListFragment.newInstance(1, currentUser.getAdmin());
                    onButtonPressed(listFragment);
                }
            }
        });

        return view;
    }

    private void actuallyCreateTheView(View view) {
        emailEditText = (EditText) view.findViewById(R.id.editText_email);
        passwordEditText = (EditText) view.findViewById(R.id.editText_pw);

        Button btnSignIn = (Button) view.findViewById(R.id.loginbtn_signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                AlertDialog alertDialog = checkFieldValidation(email, password);

                if (alertDialog != null) {
                    alertDialog.show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);

                    Model.instance.userLogin(email, password, new Model.IGetUserLoginCallback() {
                        @Override
                        public void onComplete(User user) {
                            progressBar.setVisibility(View.GONE);

                            if (user != null) {
                                MovieListFragment listFragment = MovieListFragment.newInstance(1, user.isAdmin);
                                onButtonPressed(listFragment);
                            } else {
                                showPopupMethod(v, "Authentication fails", "Please try again");
                            }
                        }
                    });
                }
            }
        });
    }

    private AlertDialog checkFieldValidation(String email, String password) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Pattern p = Patterns.EMAIL_ADDRESS;
        Matcher m = p.matcher(email);

        if (email.equals("") || email.length() == 0
                || password.equals("") || password.length() == 0)

            builder.setMessage("All fields are required.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            // Check if email id valid or not
        else if (!m.find())
            builder.setMessage("Your Email Id is Invalid.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            emailEditText.requestFocus();
                        }
                    });
        else {
            return null;
        }

        return builder.create();
    }

    private void showPopupMethod(View v, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }


    public final void onButtonPressed(Fragment frag) {
        if (mListener != null) {
            mListener.onFragmentInteractionChangeFrag(frag);
        }
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
        void onFragmentInteractionChangeFrag(Fragment frag);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_login, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_up:
                RegisterFragment registerFragment = RegisterFragment.newInstance();
                onButtonPressed(registerFragment);
                break;
            default:
                break;
        }

        return true;
    }
}
