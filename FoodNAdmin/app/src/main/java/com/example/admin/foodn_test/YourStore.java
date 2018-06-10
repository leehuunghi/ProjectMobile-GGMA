package com.example.admin.foodn_test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class YourStore extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    ListView lvList;
    ImageButton imgThemCuaHang;
    NavigationView navigationView;
    ImageView imgTuyChon;

    String[] tenQuan = { "Quán-1", "Quán-2", "Quán-3", "Quán-4", "Quán-5"};
    String[] diaChi = { "Địa chỉ", "Địa chỉ", "Địa chỉ", "Địa chỉ", "Địa chỉ"};

    Integer[] thumbnails = { R.drawable.menu_thumbnail, R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail, R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail};

    Integer[] tuyChon={R.drawable.ic_more_vert_black_24dp};

    private DrawerLayout mDrawerLayout;

    String Ten="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_store);

        addControls();

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
                            case R.id.nav_ThemQuan:
                                Intent d = new Intent(YourStore.this, AddingStore.class);
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
        getSupportActionBar().setTitle("Cửa hàng");
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
                Ten=tenQuan[i];
            }
        });

        CustomIconLabelAdapter adapter = new CustomIconLabelAdapter(
                this,
                R.layout.list_store,
                tenQuan,diaChi,
                thumbnails,tuyChon);
        // bind intrinsic ListView to custom adapter
        lvList.setAdapter(adapter);

    }

    public void showDialogDeleteStore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(YourStore.this);
        builder.setTitle(R.string.delete_store);

        //list of items
        final String[] items = getResources().getStringArray(R.array.delete_store);
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


    public void showMenu(View v)
    {
        PopupMenu popup = new PopupMenu(YourStore.this,v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.your_store_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.menuXemTruoc:
                        Bundle bundle=new Bundle();
                        bundle.putString("Ten",Ten);
                        Intent a = new Intent(YourStore.this, DetailStore.class);
                        a.putExtra("myPackage",bundle);
                        startActivity(a);
                        break;
                    case R.id.menuXoa:
                        showDialogDeleteStore();
                        break;
                    case R.id.menuChinhSua:
                        Intent e = new Intent(YourStore.this, UpdateStore.class);
                        startActivity(e);
                        break;
                    case R.id.menuQuanLy:
                        Intent f = new Intent(YourStore.this, ManageMenu.class);
                        startActivity(f);
                        break;
                }
                return true;
            }
        });
    }

    private void addControls() {
        lvList = (ListView) findViewById(R.id.listView);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        imgThemCuaHang = findViewById(R.id.imgThemCuaHang);
        navigationView = findViewById(R.id.nav_view1);
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
        Integer[] thumbnails;
        Integer[] tuyChon;

        public CustomIconLabelAdapter(Context context, int layoutToBeInflated,
                                      String[] tenQuan,
                                      String[] diaChi,
                                      Integer[] thumbnails, Integer[] tuyChon) {
            super(context, R.layout.list_store, tenQuan);
            this.context = context;
            this.thumbnails = thumbnails;
            this.tenQuan = tenQuan;
            this.diaChi=diaChi;
            this.tuyChon=tuyChon;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View row = inflater.inflate(R.layout.list_store, null);
            TextView tenQuanAn = (TextView) row.findViewById(R.id.txtTenQuan);
            TextView diaChiQuan = (TextView) row.findViewById(R.id.txtDiaChi);
            ImageView imgQuanAn = (ImageView) row.findViewById(R.id.imgQuan);
            imgTuyChon= (ImageView) row.findViewById(R.id.imgTuyChon);
            tenQuanAn.setText(tenQuan[position]);
            diaChiQuan.setText(diaChi[position]);
            imgQuanAn.setImageResource(thumbnails[position]);
            imgTuyChon.setImageResource(tuyChon[0]);

            imgTuyChon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });
            return (row);
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}