package com.example.admin.foodn_test;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class ConfirmActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        toolbar = findViewById(R.id.toolbar);
        btnConfirm=findViewById(R.id.btnConfirm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Xác nhận");
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent confirmAct = new Intent(ConfirmActivity.this, LoginActivity.class);
                startActivity(confirmAct);
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
}
