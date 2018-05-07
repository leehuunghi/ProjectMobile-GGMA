package com.example.admin.foodn_test;

import android.content.ClipData;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    DATABASE_FAV database_records;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ListView listview = (ListView) findViewById(R.id.listView2);

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
                Intent a = new Intent(FavoriteActivity.this, InfoAccount.class);
                startActivity(a);
            }
        });
        accName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(FavoriteActivity.this, InfoAccount.class);
                startActivity(a);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
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

        ////create an instance of our fake database: {[text1, text2, icon]}
        List database = new DATABASE_FAV().dbList;
        CustomFavAdapter adapter = new CustomFavAdapter(this,
                database,
                R.layout.fav_list_gui_row
        );
        listview.setAdapter(adapter);



    }//onCreate

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
            case R.id.nav_ThongTin:
                Intent d = new Intent(this, HomeActivity.class);
                startActivity(d);
                break;
            case R.id.nav_DangXuat:
                Intent e = new Intent(this, LoginActivity.class);
                startActivity(e);
                break;
        }
        return false;
    }
}
