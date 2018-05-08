package com.example.admin.foodn_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

public class AddingStore extends AppCompatActivity {

    EditText txtTenCuaHang, txtDiaChiCuaHang, txtSDTCuaHang, txtMoCua, txtDongCua;
    Button btnQuanLyMenu;
    GridView gridView;
    ImageView imgLocationCuaHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_store);

        addControls();
        addEvents();

    }

    private void addEvents() {
        btnQuanLyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intManage=new Intent(AddingStore.this,ManageMenu.class);
                startActivity(intManage);
            }
        });

        imgLocationCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intMap=new Intent(AddingStore.this,HomeActivity.class);
                startActivity(intMap);
            }
        });
    }

    private void addControls() {
        txtTenCuaHang=findViewById(R.id.txtTenCuaHang);
        txtDiaChiCuaHang=findViewById(R.id.txtDiaChiCuaHang);
        txtSDTCuaHang=findViewById(R.id.txtSDTCuaHang);
        txtMoCua=findViewById(R.id.txtMoCua);
        txtDongCua=findViewById(R.id.txtDongCua);

        btnQuanLyMenu=findViewById(R.id.btnQuanLyMenu);

        gridView=findViewById(R.id.gridView);
        imgLocationCuaHang=findViewById(R.id.imgLocationCuaHang);
    }
}
