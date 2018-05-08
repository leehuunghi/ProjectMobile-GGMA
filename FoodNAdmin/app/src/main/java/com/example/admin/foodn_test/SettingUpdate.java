package com.example.admin.foodn_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingUpdate extends AppCompatActivity {

    EditText txtUBanKinh, txtUCheDoXem;
    Button btnCapNhatSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_update);
        
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnCapNhatSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void addControls() {
        txtUBanKinh=findViewById(R.id.txtUBanKinh);
        txtUCheDoXem=findViewById(R.id.txtUCheDoXem);

        btnCapNhatSetting=findViewById(R.id.btnCapNhatSetting);
    }
}
