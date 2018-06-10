package com.example.admin.foodn_test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddingFood extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    EditText txtTenMonThem, txtGiaThem, txtDonViThem,txtNhomThem;

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food);

        txtTenMonThem = findViewById(R.id.txtTenMonThem);
        txtGiaThem = findViewById(R.id.txtGiaThem);
        txtDonViThem = findViewById(R.id.txtDonViThem);
        txtNhomThem = findViewById(R.id.txtNhomThem);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Thêm món ăn");
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }
}
