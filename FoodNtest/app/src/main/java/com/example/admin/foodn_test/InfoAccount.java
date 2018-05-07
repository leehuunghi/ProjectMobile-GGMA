package com.example.admin.foodn_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoAccount extends AppCompatActivity {

    TextView txtSDT, txtHoTen, txtMatKhau, txtNgaySinh, txtGioiTinh;
    Button btnChinhSua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_account);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infUpdate=new Intent(InfoAccount.this, InfoUpdate.class);
                startActivity(infUpdate);
            }
        });
    }

    private void addControls() {
        txtSDT = findViewById(R.id.txtSDT);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtMatKhau=findViewById(R.id.txtMatKhau);
        txtNgaySinh=findViewById(R.id.txtNgaySinh);
        txtGioiTinh=findViewById(R.id.txtGioiTinh);

        btnChinhSua=findViewById(R.id.btnChinhSua);
    }
}
