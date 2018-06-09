package com.example.admin.foodn_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

public class UpdateStore extends AppCompatActivity {

    EditText txtTenCuaHangUpdate, txtDiaChiCuaHangUpdate, txtSDTCuaHangUpdate, txtMoCuaUpdate, txtDongCuaUpdate;
    Button btnUpdateStore;
    ImageView imgLocationCuaHangUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_store);

        addControls();
        addEvents();
    }

    private void addEvents() {

        imgLocationCuaHangUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intMap=new Intent(UpdateStore.this,HomeActivity.class);
                startActivity(intMap);
            }
        });
    }

    private void addControls() {
        txtTenCuaHangUpdate = findViewById(R.id.txtTenCuaHangUpdate);
        txtDiaChiCuaHangUpdate=findViewById(R.id.txtDiaChiCuaHangUpdate);
        txtSDTCuaHangUpdate=findViewById(R.id.txtSDTCuaHangUpdate);
        txtMoCuaUpdate=findViewById(R.id.txtMoCuaUpdate);
        txtDongCuaUpdate=findViewById(R.id.txtDongCuaUpdate);
        btnUpdateStore=findViewById(R.id.btnUpdateStore);
        imgLocationCuaHangUpdate=findViewById(R.id.imgLocationCuaHangUpdate);
    }
}
