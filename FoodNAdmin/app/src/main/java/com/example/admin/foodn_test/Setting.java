package com.example.admin.foodn_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Setting extends AppCompatActivity {

    TextView txtBanKinh, txtCheDoXem;
    Button btnChinhSuaSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnChinhSuaSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateSetting=new Intent(Setting.this,SettingUpdate.class);
                startActivity(updateSetting);
            }
        });
    }

    private void addControls() {
        txtBanKinh=findViewById(R.id.txtBanKinh);
        txtCheDoXem=findViewById(R.id.txtCheDoXem);

        btnChinhSuaSetting=findViewById(R.id.btnChinhSuaSetting);
    }
}
