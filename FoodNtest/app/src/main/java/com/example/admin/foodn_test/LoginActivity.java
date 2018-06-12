package com.example.admin.foodn_test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.config.Configuaration;
import com.example.admin.model.User;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
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


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.leave)
                .setPositiveButton("Có", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void showLoginAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(getString(R.string.login_wrong));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,null);
    AlertDialog dialog = builder.create();
    // display dialog
    dialog.show();
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
            LoginAccount loginAccount = new LoginAccount();
            loginAccount.execute();
        }
    }

    private boolean isNumberValid(String txtSdt) {
        return android.util.Patterns.PHONE.matcher(txtSdt).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    class LoginAccount extends AsyncTask<String, Void, String>
    {
        boolean flagLogin = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!flagLogin) showLoginAlert();
            else LoginActivity.this.finish();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_LOGIN);
                request.addProperty(Configuaration.PARAMETER_SDT, txtSdt.getText().toString());
                request.addProperty(Configuaration.PARAMETER_matKhau, txtPass.getText().toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_LOGIN, envelope);

                SoapObject so= (SoapObject) envelope.getResponse();

                if(so.hasProperty("ID_User")){
                        flagLogin = true;
                        GlobalVariable.MyUser = new User();
                        GlobalVariable.MyUser.setId(Integer.parseInt(so.getPropertyAsString("ID_User")));
                        GlobalVariable.MyUser.setHoten(so.getPropertyAsString("HoTen"));
                        GlobalVariable.MyUser.setSdt(so.getPropertyAsString("SDT_User"));
                        GlobalVariable.MyUser.setMatkhau(so.getPropertyAsString("MatKhau"));
                        GlobalVariable.MyUser.setNgaysinh(so.getPropertyAsString("NgaySinh"));
                        GlobalVariable.MyUser.setGioitinh(so.getPropertyAsString("GioiTinh"));
                        GlobalVariable.MyUser.setAva(StringToBitMap(so.getPropertyAsString("Avatar")));
                        Intent homeAct = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homeAct);
                }
            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return null;
        }
    }

    private Bitmap StringToBitMap(String hinhAnh) {
        try {
            byte[] encodeByte = Base64.decode(hinhAnh);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
