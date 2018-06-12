package com.example.admin.foodn_test;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingUpdate extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button btnCapNhatSetting;
    LinearLayout sugRad;
    LinearLayout viewMode;
    TextView sugRadResult;
    TextView viewModeResult;
    private DrawerLayout mDrawerLayout;
    String checkeditemVal;
    String checkeditemVMVal ;
    int checkedItemRad;
    int checkedItemVM;
    String tempRad;
    String tempVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_update);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        View headerview = navigationView.getHeaderView(0);
        TextView accName = (TextView) headerview.findViewById(R.id.accName);
        accName.setText(GlobalVariable.MyUser.getHoten());
        ImageView accAva = headerview.findViewById(R.id.accAva);
        accAva.setImageBitmap(GlobalVariable.MyUser.getAva());
        accAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(SettingUpdate.this, InfoAccount.class);
                startActivity(a);
            }
        });
        accName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(SettingUpdate.this, InfoAccount.class);
                startActivity(a);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setTitle("Tùy chỉnh");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
        addControls();
        btnCapNhatSetting.setEnabled(false);
        readupRad();
        readupVM();
        addEvents();
        btnCapNhatSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeDownRad(sugRadResult.getText().toString());
                writeDownVM(viewModeResult.getText().toString());
                Toast.makeText(getBaseContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                btnCapNhatSetting.setEnabled(false);
            }
        });
    }

    public void readupRad() {
        SharedPreferences getSettingRadius = this.getSharedPreferences("settingsRadius", Context.MODE_PRIVATE);
        if ( (getSettingRadius != null) && (getSettingRadius.contains("sugRadRes")) ) {
            String temp = getSettingRadius.getString("sugRadRes","5 km");
            sugRadResult.setText(temp);
        }
    }

    public void readupVM() {
        SharedPreferences getSettingVM = this.getSharedPreferences("settingsVM", Context.MODE_PRIVATE);
        if ( (getSettingVM != null) && (getSettingVM.contains("viewModeRes")) ) {
            String temp = getSettingVM.getString("viewModeRes","Vệ tinh");
            viewModeResult.setText(temp);
        }
    }

    public void writeDownRad(String rad) {
        SharedPreferences settingsRadius = this.getSharedPreferences("settingsRadius", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = settingsRadius.edit();
        myEditor.putString("sugRadRes", rad);
        myEditor.commit();
    }

    public void writeDownVM(String vm) {
        SharedPreferences settingsVM = this.getSharedPreferences("settingsVM", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor2 = settingsVM.edit();
        myEditor2.putString("viewModeRes", vm);
        myEditor2.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        tempRad = sugRadResult.getText().toString();

        if (sugRadResult.getText().equals("5 km")) checkedItemRad = 0;
        if (sugRadResult.getText().equals("10 km")) checkedItemRad = 1;
        if (sugRadResult.getText().equals("15 km")) checkedItemRad = 2;

        //list of items
        final String[] items = getResources().getStringArray(R.array.radius);
        builder.setSingleChoiceItems(items, checkedItemRad,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            checkeditemVal = "5 km";
                            GlobalVariable.radius=5000;

                        }
                        if (which == 1) {
                            checkeditemVal = "10 km";
                            GlobalVariable.radius=10000;
                        }
                        if (which == 2) {
                            checkeditemVal = "15 km";
                        }
                    }
                });

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sugRadResult.setText(checkeditemVal);
                        if (!tempRad.equals(checkeditemVal)) btnCapNhatSetting.setEnabled(true);
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

        tempVM = viewModeResult.getText().toString();

        if (viewModeResult.getText().equals("Thường")) checkedItemVM = 0;
        if (viewModeResult.getText().equals("Vệ tinh")) checkedItemVM = 1;

        //list of items
        final String[] items = getResources().getStringArray(R.array.view_mode);
        builder.setSingleChoiceItems(items, checkedItemVM,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            checkeditemVMVal = "Thường";
                        }
                        if (which == 1) {
                            checkeditemVMVal = "Vệ tinh";
                        }
                    }
                });

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModeResult.setText(checkeditemVMVal);
                        if (!tempVM.equals(checkeditemVMVal)) btnCapNhatSetting.setEnabled(true);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_TrangChu:
                Intent a = new Intent(this, HomeActivity.class);
                startActivity(a);
                break;
            case R.id.nav_YeuThich:
                Intent b = new Intent(this, FavoriteActivity.class);
                startActivity(b);
                break;
            case R.id.nav_TuyChinh:
                Intent c = new Intent(this, SettingUpdate.class);
                startActivity(c);
                break;
            case R.id.nav_DangXuat:
                Intent e = new Intent(this, LoginActivity.class);
                startActivity(e);
                this.finish();
                break;
        }
        return false;
    }
}
