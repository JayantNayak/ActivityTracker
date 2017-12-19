package com.hobby.jayant.activitytracker.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hobby.jayant.activitytracker.R;
import com.hobby.jayant.activitytracker.models.User;
import com.hobby.jayant.activitytracker.services.ActivityTrackerService;
import com.hobby.jayant.activitytracker.services.UserService;

import java.net.HttpURLConnection;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity{
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mFirstNameView;
    private AutoCompleteTextView mLastNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignUpButton;
    private ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mFirstNameView = (AutoCompleteTextView) findViewById(R.id.firstname);
        mLastNameView = (AutoCompleteTextView) findViewById(R.id.lastname);




        mPasswordView = (EditText) findViewById(R.id.createpassword);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginFormView = findViewById(R.id.signup_form);
        mProgressView = findViewById(R.id.signup_progress);

         mEmailSignUpButton = (Button) findViewById(R.id.user_sign_up_button);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }

    private void attemptLogin() {
        /*if (mAuthTask != null) {
            return;
        }*/

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String firstname = mFirstNameView.getText().toString();
        String lastname = mLastNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // check for valid lastname
        if (TextUtils.isEmpty(lastname)){
            mLastNameView.setError("Please enter a valid last name");
            focusView = mLastNameView;
            cancel = true;

        }
        // check for valid firstname
        if (TextUtils.isEmpty(firstname)){
            mFirstNameView.setError("Please enter a valid first name");
            focusView = mFirstNameView;
            cancel = true;

        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            //mAuthTask = new SigninActivity.UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);



            progressDialog.setMessage("Signing up.");
            progressDialog.show();


            User signUpUser = new User( firstname,  lastname, email, password);
            UserService userService  = ActivityTrackerService.getUserService();
            Call<Void> userCall =  userService.saveUser(signUpUser);

            userCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    response.headers().toString();
                    Headers header = response.headers();
                    if(response.code()==  HttpURLConnection.HTTP_CONFLICT){
                        displayToast("Email id already registered with another user");
                    }
                    else if(response.code()==  HttpURLConnection.HTTP_CREATED || response.code()==  HttpURLConnection.HTTP_OK){
                        displayToast("User created. Please login");
                        Intent myIntent = new Intent(SignupActivity.this, SigninActivity.class);
                        SignupActivity.this.startActivity(myIntent);

                    }else{
                        displayToast("Server Error! Try again later.");
                    }
                    progressDialog.dismiss();

                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    //final TextView textView = (TextView) findViewById(R.id.textView);
                    //textView.setText("Something went wrong: " + t.getMessage());
                    displayToast("Something went wrong." );
                    progressDialog.dismiss();
                }
            });




        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    private void displayToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }

}
