package com.example.manvish.scrollingactivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    public OneFragment() {
        // Required empty public constructor
    }

    SignUpResponse signUpResponsesData;
    EditText emailId, password, name;
    Button signUp;

    View minflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        minflater= inflater.inflate(R.layout.fragment_one, container, false);

        initiaiseview();

        return  minflater;
    }

    private void initiaiseview() {
        name = (EditText) minflater.findViewById(R.id.username);
        emailId = (EditText) minflater.findViewById(R.id.email);
        password = (EditText) minflater.findViewById(R.id.password);
        signUp = (Button) minflater.findViewById(R.id.signUp);
        // implement setOnClickListener event on sign up Button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate the fields and call sign method to implement the api
                if (validate(name) && validateEmail() && validate(password)) {
                    signUp();
                }
            }
        });
    }

    private void signUp() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        // enqueue is used for callback response and error
        (Api.getClient().registration(name.getText().toString().trim(),
                emailId.getText().toString().trim(),
                password.getText().toString().trim(),
                "email")).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                signUpResponsesData = response.body();
                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("response", t.getStackTrace().toString());
                progressDialog.dismiss();

            }
        });
    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true; // returns true if field is not empty
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }
    private boolean validateEmail() {
        String email = emailId.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            emailId.setError("Email is not valid.");
            emailId.requestFocus();
            return false;
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
