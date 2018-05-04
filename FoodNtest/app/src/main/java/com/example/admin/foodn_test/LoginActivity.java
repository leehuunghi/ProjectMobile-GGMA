package com.example.admin.foodn_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    // UI references.
    private TextView txtSdt;
    private EditText txtPass;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        txtSdt = findViewById(R.id.txtSdt);
        txtPass = findViewById(R.id.txtPass);
        Button btnSignup = findViewById(R.id.btnSignupForm);
        btnSignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupAct = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupAct);
            }
        });
        txtPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        txtSdt.setError(null);
        txtPass.setError(null);

        // Store values at the time of the login attempt.
        String sdt = txtSdt.getText().toString();
        String password = txtPass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            txtPass.setError(getString(R.string.error_invalid_password));
            focusView = txtPass;
            cancel = true;
        }

        // Check for a valid phone address.
        if (TextUtils.isEmpty(sdt)) {
            txtSdt.setError(getString(R.string.error_field_required));
            focusView = txtSdt;
            cancel = true;
        } else if (!isNumberValid(sdt)) {
            txtSdt.setError(getString(R.string.error_invalid_sdt));
            focusView = txtSdt;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if (txtSdt.getText().toString().equals("123456") && txtPass.getText().toString().equals("123456")) {
                Intent homeAct = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(homeAct);
            } else {
                Toast.makeText(this,
                        "Huhu sai òi"
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNumberValid(String txtSdt) {
        return android.util.Patterns.PHONE.matcher(txtSdt).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
