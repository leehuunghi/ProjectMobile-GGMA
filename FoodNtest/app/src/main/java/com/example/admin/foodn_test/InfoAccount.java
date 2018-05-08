package com.example.admin.foodn_test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoAccount extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtSDT, txtHoTen, txtMatKhau, txtNgaySinh, txtGioiTinh;
    Button btnChinhSua;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_account);

        addControls();
        addEvents();

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
        accName.setText("Tên tài khoản");
        ImageView accAva = headerview.findViewById(R.id.accAva);
        accAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(InfoAccount.this, InfoAccount.class);
                startActivity(a);
            }
        });
        accName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(InfoAccount.this, InfoAccount.class);
                startActivity(a);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
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
        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infUpdate = new Intent(InfoAccount.this, InfoUpdate.class);
                startActivity(infUpdate);
            }
        });
    }

    private void addControls() {
        txtSDT = findViewById(R.id.txtSDT);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtGioiTinh = findViewById(R.id.txtGioiTinh);
        btnChinhSua = findViewById(R.id.btnChinhSua);
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
