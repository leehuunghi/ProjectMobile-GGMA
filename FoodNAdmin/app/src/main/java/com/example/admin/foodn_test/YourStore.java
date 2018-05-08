package com.example.admin.foodn_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class YourStore extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    ListView lvList;
    ImageButton imgThemCuaHang;

    String[] tenQuan = { "Quán-1", "Quán-2", "Quán-3", "Quán-4", "Quán-5"};
    String[] diaChi = { "Địa chỉ", "Địa chỉ", "Địa chỉ", "Địa chỉ", "Địa chỉ"};
    String[] giaTien = { "Gía tiền","Gía tiền","Gía tiền","Gía tiền", "Gía tiền"};

    Integer[] thumbnails = { R.drawable.menu_thumbnail, R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail, R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail};

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_store);

        lvList = (ListView) findViewById(R.id.listView);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        imgThemCuaHang = findViewById(R.id.imgThemCuaHang);

        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        // close drawer when item is tapped
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.navQuanCuaBan:
                                Intent a = new Intent(YourStore.this, YourStore.class);
                                startActivity(a);
                                break;
                            case R.id.nav_ThongTin:
                                Intent d = new Intent(YourStore.this, ThongTinAppUpdate.class);
                                startActivity(d);
                                break;
                            case R.id.nav_DangXuat:
                                Intent e = new Intent(YourStore.this, LoginActivity.class);
                                startActivity(e);
                                break;
                        }



                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cửa hàng của bạn");
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

        imgThemCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intAddStore = new Intent(YourStore.this, AddingStore.class);
                startActivity(intAddStore);
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        CustomIconLabelAdapter adapter = new CustomIconLabelAdapter(
                this,
                R.layout.list_store,
                tenQuan,diaChi,giaTien,
                thumbnails);
        // bind intrinsic ListView to custom adapter
        lvList.setAdapter(adapter);
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


    class CustomIconLabelAdapter extends ArrayAdapter<String> {
        Context context;
        String[] tenQuan;
        String[] diaChi;
        String[] giaTien;
        Integer[] thumbnails;

        public CustomIconLabelAdapter(Context context, int layoutToBeInflated,
                                      String[] tenQuan,
                                              String[] diaChi,
                                              String[] giaTien,
                                              Integer[] thumbnails) {
            super(context, R.layout.list_store, tenQuan);
            this.context = context;
            this.thumbnails = thumbnails;
            this.tenQuan = tenQuan;
            this.giaTien=giaTien;
            this.diaChi=diaChi;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View row = inflater.inflate(R.layout.list_store, null);
            TextView tenQuanAn = (TextView) row.findViewById(R.id.txtTenQuan);
            TextView diaChiQuan = (TextView) row.findViewById(R.id.txtDiaChi);
            TextView giaTienTB = (TextView) row.findViewById(R.id.txtGiaTien);
            ImageView imgQuanAn = (ImageView) row.findViewById(R.id.imgQuan);
            tenQuanAn.setText(tenQuan[position]);
            diaChiQuan.setText(diaChi[position]);
            giaTienTB.setText(giaTien[position]);
            imgQuanAn.setImageResource(thumbnails[position]);
            return (row);
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}
