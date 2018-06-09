package com.example.admin.foodn_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UpdateFood extends AppCompatActivity {

    EditText txtTenMonUpdate, txtGiaUpdate, txtDonViUpdate,txtNhomUpdate;
    Button btnUpdateFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        addEvents();
        addControls();
    }

    private void addEvents() {

    }

    private void addControls() {
        txtTenMonUpdate=findViewById(R.id.txtTenMonUpdate);
        txtGiaUpdate=findViewById(R.id.txtGiaUpdate);
        txtDonViUpdate=findViewById(R.id.txtDonViUpdate);
        txtNhomUpdate=findViewById(R.id.txtNhomUpdate);
        btnUpdateFood=findViewById(R.id.btnUpdateFood);
    }
}