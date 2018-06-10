package com.example.admin.foodn_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Chỉnh sửa món ăn");
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

    }

    private void addControls() {
        txtTenMonUpdate=findViewById(R.id.txtTenMonUpdate);
        txtGiaUpdate=findViewById(R.id.txtGiaUpdate);
        txtDonViUpdate=findViewById(R.id.txtDonViUpdate);
        txtNhomUpdate=findViewById(R.id.txtNhomUpdate);
        btnUpdateFood=findViewById(R.id.btnUpdateFood);
    }
}