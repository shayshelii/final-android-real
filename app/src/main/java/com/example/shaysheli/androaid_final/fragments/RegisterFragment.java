package com.example.shaysheli.androaid_final.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.shaysheli.androaid_final.Model.User;
import com.example.shaysheli.androaid_final.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private static View v;
    private static EditText txtUserName;
    private static EditText txtEmail;
    private static EditText txtPhone;
    private static EditText txtLocation;
    private static EditText txtPassword;
    private static EditText txtConfirmPassword;
    private static CheckBox cbAgree;
    private static Button btnSignUp;

    public RegisterFragment() {}


    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        v =  inflater.inflate(R.layout.fragment_register, container, false);
        txtUserName = (EditText) v.findViewById(R.id.regfullName);
        txtEmail = (EditText) v.findViewById(R.id.reguserEmailId);
        txtPhone = (EditText) v.findViewById(R.id.regmobileNumber);
        txtLocation = (EditText) v.findViewById(R.id.reglocation);
        txtPassword = (EditText) v.findViewById(R.id.regpassword);
        txtConfirmPassword = (EditText) v.findViewById(R.id.regconfirmPassword);
        cbAgree = (CheckBox) v.findViewById(R.id.regterms_conditions);
        btnSignUp = (Button) v.findViewById(R.id.regsignUpBtn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog DialogToShow = checkFields();

                if (DialogToShow != null)
                    DialogToShow.show();
                else
                    SignUp();
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentRegisterInteraction();
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
        void onFragmentRegisterInteraction();
    }

    public AlertDialog checkFields(){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        // Get all edittext texts
        String getFullName = txtUserName.getText().toString();
        String getEmailId = txtEmail.getText().toString();
        String getMobileNumber = txtPhone.getText().toString();
        String getLocation = txtLocation.getText().toString();
        String getPassword = txtPassword.getText().toString();
        String getConfirmPassword = txtConfirmPassword.getText().toString();

        Pattern p = Patterns.EMAIL_ADDRESS;
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

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
                        txtEmail.requestFocus();
                    }
                });

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            builder.setMessage("Both password doesn't match.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            txtPassword.requestFocus();
                        }
                    });

            // Make sure user should check Terms and Conditions checkbox
        else if (!cbAgree.isChecked())
            builder.setMessage("Please select Terms and Conditions.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    });
        else
            return null;


        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void SignUp(){
        User newUser = new User(txtUserName.getText().toString(),
                                txtEmail.getText().toString(),
                                false);
        // TODO: Update DB

    }
}
