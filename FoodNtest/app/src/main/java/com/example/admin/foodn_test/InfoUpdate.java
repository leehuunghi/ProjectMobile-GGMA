package com.example.admin.foodn_test;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.config.Configuaration;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL;

public class InfoUpdate extends AppCompatActivity {

    EditText txtUSDT, txtUHoTen, txtUMatKhau, txtUNgaySinh;
    Button btnCapNhat;
    ImageButton btnEdit;
    ImageView imgAva;
    RadioGroup radioGender;
    RadioButton tempBtn;
    Bitmap bmpAva;
    String encoded;
    private static int LOAD_IMAGE_RESULTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update);

        addControls();
        addEvents();

        imgAva.setImageBitmap(GlobalVariable.MyUser.getAva());
        txtUSDT.setText(GlobalVariable.MyUser.getSdt());
        txtUHoTen.setText(GlobalVariable.MyUser.getHoten());
        txtUMatKhau.setText(GlobalVariable.MyUser.getMatkhau());
        txtUNgaySinh.setText(GlobalVariable.MyUser.getNgaysinh());
        if (GlobalVariable.MyUser.getGioitinh().equals("Nam")) radioGender.check(R.id.radioMale);
        if (GlobalVariable.MyUser.getGioitinh().equals("Nữ")) radioGender.check(R.id.radioFemale);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InfoUpdate.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                int selectedId = radioGender.getCheckedRadioButtonId();
                tempBtn = (RadioButton) findViewById(selectedId);

                BitmapDrawable drawable = (BitmapDrawable) imgAva.getDrawable();
                bmpAva = drawable.getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bmpAva.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                UpdateUserInfo updateUserInfo = new UpdateUserInfo();
                updateUserInfo.execute();
            }
        });

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

    private void addEvents() {
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    class UpdateUserInfo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_UPDATE_USER);
                request.addProperty(Configuaration.PARAMETER_ID, GlobalVariable.MyUser.getId());
                request.addProperty(Configuaration.PARAMETER_NAME, txtUHoTen.getText().toString() );
                request.addProperty(Configuaration.PARAMETER_PHONE, txtUSDT.getText().toString() );
                request.addProperty(Configuaration.PARAMETER_DATE, txtUNgaySinh.getText().toString() );
                request.addProperty(Configuaration.PARAMETER_SEX, tempBtn.getText().toString() );
                request.addProperty(Configuaration.PARAMETER_PASSWORD, txtUMatKhau.getText().toString() );
                request.addProperty(Configuaration.PARAMETER_AVATAR, encoded );

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_UPDATE_USER, envelope);

                SoapObject so= (SoapObject) envelope.bodyIn;
            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
                return null;
            }
            GlobalVariable.MyUser.setHoten(txtUHoTen.getText().toString());
            GlobalVariable.MyUser.setSdt(txtUSDT.getText().toString());
            GlobalVariable.MyUser.setNgaysinh(txtUNgaySinh.getText().toString());
            GlobalVariable.MyUser.setGioitinh(tempBtn.getText().toString());
            GlobalVariable.MyUser.setMatkhau(txtUMatKhau.getText().toString());
            GlobalVariable.MyUser.setAva(bmpAva);
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent intent) {
        super.onActivityResult(requestCode, resultcode, intent);
        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultcode == RESULT_OK && intent != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = intent.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            imgAva = findViewById(R.id.imgAvaEdit);
            imgAva.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    private Bitmap StringToBitMap(String hinhAnh) {
        try {
            byte[] encodeByte = org.kobjects.base64.Base64.decode(hinhAnh);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void addControls() {
        txtUSDT = findViewById(R.id.txtUSDT);
        txtUHoTen = findViewById(R.id.txtUHoTen);
        txtUMatKhau = findViewById(R.id.txtUMatKhau);
        txtUNgaySinh = findViewById(R.id.txtUNgaySinh);
        btnCapNhat = findViewById(R.id.btnCapNhatUser);
        imgAva = findViewById(R.id.imgAvaEdit);
        radioGender = findViewById(R.id.radioGender);
        btnEdit = findViewById(R.id.btnEdit);
    }
}
