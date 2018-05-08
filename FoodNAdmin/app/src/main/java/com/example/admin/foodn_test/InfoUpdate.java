package com.example.admin.foodn_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class InfoUpdate extends AppCompatActivity {

    EditText txtUSDT, txtUHoTen, txtUMatKhau, txtUNgaySinh;
    Button btnCapNhat;
    ImageButton btnEdit;
    ImageView profileAva;
    SharedPreferences sharedPreferences;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update);

        addControls();
        addEvents();


        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        switch(requestCode) {
//            case 0:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    profileAva.setImageURI(selectedImage);
//                }
//
//                break;
//            case 1:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    profileAva.setImageURI(selectedImage);
//                }
//                break;
//        }
    }

    private void addControls() {
        txtUSDT = findViewById(R.id.txtUSDT);
        txtUHoTen = findViewById(R.id.txtUHoTen);
        txtUMatKhau = findViewById(R.id.txtUMatKhau);
        txtUNgaySinh = findViewById(R.id.txtUNgaySinh);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnEdit = findViewById(R.id.btnEdit);
        profileAva = findViewById(R.id.imgAvatar);
    }
}
