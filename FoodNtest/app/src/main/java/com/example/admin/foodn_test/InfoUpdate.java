package com.example.admin.foodn_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InfoUpdate extends AppCompatActivity {

    EditText txtUSDT, txtUHoTen, txtUMatKhau, txtUNgaySinh, txtUGioiTinh;
    Button btnCapNhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void addControls() {
        txtUSDT = findViewById(R.id.txtUSDT);
        txtUHoTen = findViewById(R.id.txtUHoTen);
        txtUMatKhau=findViewById(R.id.txtUMatKhau);
        txtUNgaySinh=findViewById(R.id.txtUNgaySinh);
        txtUGioiTinh=findViewById(R.id.txtUGioiTinh);

        btnCapNhat=findViewById(R.id.btnCapNhat);
    }
}
