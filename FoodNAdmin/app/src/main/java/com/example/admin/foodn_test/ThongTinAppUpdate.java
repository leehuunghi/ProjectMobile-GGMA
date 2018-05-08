package com.example.admin.foodn_test;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThongTinAppUpdate extends AppCompatActivity {

    Button btnCapNhatSetting;
    LinearLayout sugRad;
    LinearLayout viewMode;
    TextView sugRadResult;
    TextView viewModeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_app_update);
    }

    private void addEvents() {

        sugRad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRadius();
            }
        });

        viewMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogViewMode();
            }
        });

        btnCapNhatSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void showDialogRadius() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinAppUpdate.this);


        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        String input = items[which].toString();
//                        sugRadResult.setText(input);
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    public void showDialogViewMode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinAppUpdate.this);


        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        String input = items[which].toString();
//                        sugRadResult.setText(input);
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    private void addControls() {
        sugRad = findViewById(R.id.sugRad);
        viewMode = findViewById(R.id.viewMode);
        sugRadResult = findViewById(R.id.sugRadResult);
        viewModeResult = findViewById(R.id.viewModeResult);
        btnCapNhatSetting=findViewById(R.id.btnCapNhatSetting);
    }
}