package com.example.admin.foodn_test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.admin.config.Configuaration;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.HttpTransportSE;;

public class SignupActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    RadioGroup radioGender;
    RadioButton tempBtn;
    EditText txtSdt ,txtHoTen, txtPass, txtDob;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addControl();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đăng ký");
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Button btnSignUp = findViewById(R.id.btnSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                int selectedId = radioGender.getCheckedRadioButtonId();
                tempBtn = (RadioButton) findViewById(selectedId);

                SignUpAccount signUpAccount = new SignUpAccount();
                signUpAccount.execute();
            }
        });
    }

    private void addControl() {
        txtSdt = findViewById(R.id.txtSdt);
        txtHoTen = findViewById(R.id.txtHoten);
        txtPass = findViewById(R.id.txtPass);
        txtDob = findViewById(R.id.txtDob);
        radioGender = findViewById(R.id.radioGendSu);
        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Đang đăng ký. Vui lòng đợi");
        progressDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class SignUpAccount extends AsyncTask<String, Void, String>
    {
        private  boolean flagAccess=false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(!flagAccess){
                Toast.makeText(SignupActivity.this,
                        "Đăng ký thất bại!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_SIGN_UP);
                request.addProperty(Configuaration.PARAMETER_SDT, txtSdt.getText().toString());
                request.addProperty(Configuaration.PARAMETER_hoten, txtHoTen.getText().toString());
                request.addProperty(Configuaration.PARAMETER_matKhau, txtPass.getText().toString());
                request.addProperty(Configuaration.PARAMETER_gioiTinh, tempBtn.getText().toString());
                request.addProperty(Configuaration.PARAMETER_ngaySinh, txtDob.getText().toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_SIGN_UP, envelope);

                SoapObject data= (SoapObject) envelope.bodyIn;
                if(data.hasProperty("signUpResult")){
                    if(Boolean.parseBoolean(data.getPropertyAsString("signUpResult")))
                    {
                        flagAccess=true;
                        Intent confirmAct = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(confirmAct);
                    }
                }
            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return null;
        }
    }
}
