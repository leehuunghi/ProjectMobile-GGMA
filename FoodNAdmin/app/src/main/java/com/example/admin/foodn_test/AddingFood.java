package com.example.admin.foodn_test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddingFood extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    EditText txtTenMonThem, txtGiaThem, txtDonViThem,txtNhomThem;

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food);

        txtTenMonThem=findViewById(R.id.txtTenMonThem);
        txtGiaThem=findViewById(R.id.txtGiaThem);
        txtDonViThem=findViewById(R.id.txtDonViThem);
        txtNhomThem=findViewById(R.id.txtNhomThem);
        mDrawerLayout=findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.navQuanCuaBan:
                                Intent a = new Intent(AddingFood.this, YourStore.class);
                                startActivity(a);
                                break;
                            case R.id.nav_ThongTin:
                                Intent d = new Intent(AddingFood.this, ThongTinApp.class);
                                startActivity(d);
                                break;
                            case R.id.nav_DangXuat:
                                Intent e = new Intent(AddingFood.this, LoginActivity.class);
                                startActivity(e);
                                break;
                        }


                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Re
                        // spond when the drawer's position changes
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


        View headerview = navigationView.getHeaderView(0);
        TextView accName = (TextView) headerview.findViewById(R.id.accName);
        accName.setText("Tên tài khoản");
        ImageView accAva = headerview.findViewById(R.id.accAva);
        accAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(AddingFood.this, InfoAccount.class);
                startActivity(a);
            }
        });
        accName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(AddingFood.this, InfoAccount.class);
                startActivity(a);
            }
        });
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }
}
