package com.example.admin.foodn_test;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingUpdate extends AppCompatActivity {

    Button btnCapNhatSetting;
    LinearLayout sugRad;
    LinearLayout viewMode;
    TextView sugRadResult;
    TextView viewModeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_update);
        
        addControls();
        addEvents();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingUpdate.this);
        builder.setTitle(R.string.suggested_radius);

        //list of items
        final String[] items = getResources().getStringArray(R.array.radius);
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

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
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingUpdate.this);
        builder.setTitle(R.string.view_mode);

        //list of items
        final String[] items = getResources().getStringArray(R.array.view_mode);
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

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
