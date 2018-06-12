package com.example.admin.foodn_test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.config.Configuaration;
import com.example.admin.model.Store;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class YourStore extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    ListView lvList;
    ImageButton imgThemCuaHang;
    NavigationView navigationView;

    private DrawerLayout mDrawerLayout;

    List<Store> dsStore = new ArrayList<>();

    CustomIconLabelAdapter adapter;

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

            }
        });

        adapter = new CustomIconLabelAdapter(
                YourStore.this,
               dsStore,
                R.layout.list_store);
        // bind intrinsic ListView to custom adapter
        lvList.setAdapter(adapter);

        TaskFindAllStore taskFindAllStore = new TaskFindAllStore();
        taskFindAllStore.execute();

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

    public class CustomIconLabelAdapter extends BaseAdapter {

        Context context;
        int layoutToBeInflated;
        List<Store> dbList;


        public CustomIconLabelAdapter(Context context, List<Store>
                databaseList, int resource) {
            this.context = context;
            this.dbList = databaseList;
            layoutToBeInflated = resource;
        }


        @Override
        public int getCount() {
            return dbList.size();
        }

        @Override
        public Store getItem(int position) {
            return dbList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
// use View-Holder pattern to reduce calls to inflate, findViewById
// holder is a POJO for the GUI rows [textview1,textview2, img1, img2]
            MyViewHolder holder;
// hopefully convertView is a scrapview already made (but out of sight)
            View row = convertView;
            List<ImageView> rating=new ArrayList<>();
// has this row-layout been already created?
            if (row == null) {
// first time this row has to be created: (1) inflate custom layout
// holding images and text, (2) invoke findViewById to access its
// sub-components
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutToBeInflated, null);
                holder = new MyViewHolder();
                // plumbing - provide access to each widget in the inflated layout
// (two images & two lines of text)
                holder = new MyViewHolder();
                holder.tenQuanAn = (TextView) row.findViewById(R.id.txtTenQuan);
                holder.diaChiQuan = (TextView) row.findViewById(R.id.txtDiaChi);
                holder.imgQuanAn = (ImageView) row.findViewById(R.id.imgQuan);
                holder.imgTuyChon = (ImageView) row.findViewById(R.id.imgTuyChon);
                holder.rate = (TextView) row.findViewById(R.id.rate);

                holder.rating1=row.findViewById(R.id.rating1);
                holder.rating2=row.findViewById(R.id.rating2);
                holder.rating3=row.findViewById(R.id.rating3);
                holder.rating4=row.findViewById(R.id.rating4);
                holder.rating5=row.findViewById(R.id.rating5);



// identify this row with the POJO holder just created
                row.setTag(holder);
            } else {
// row was already created- no need to inflate and invoke findViewById
// getTag() returns the object originally stored in this view
                holder = (MyViewHolder) row.getTag();
            }
            rating.add(holder.rating1);
            rating.add(holder.rating2);
            rating.add(holder.rating3);
            rating.add(holder.rating4);
            rating.add(holder.rating5);
// enter(or restore) data that goes in this frame (from database 'position')
            final Store dbRec = getItem(position);
            holder.tenQuanAn.setText(dbRec.getTenCuaHang());
            holder.diaChiQuan.setText(dbRec.getDiaChi());
            holder.imgQuanAn.setImageBitmap(dbRec.getHinhAnh());
            holder.imgTuyChon.setImageResource(R.drawable.ic_more_vert_black_24dp);
            holder.rate.setText(dbRec.getTbDanhGia().toString());

            //hiển thị sao
            int Star;
            int temp = (int) (dbRec.getTbDanhGia() * 10) % 10;
            if (temp > 5) {

                Star = (int) (dbRec.getTbDanhGia() * 10 - temp) / 10 + 1;
            } else {
                Star = (int) (dbRec.getTbDanhGia() * 10 - temp) / 10;
            }

            for (int k = 0; k < Star; k++) {
                rating.get(k).setImageResource(R.drawable.ratefull);
            }

            if (Star != 5) {
                for (int j = Star; j < 5; j++) {
                    rating.get(j).setImageResource(R.drawable.rateempty);
                }
            }

            holder.imgTuyChon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(YourStore.this, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.your_store_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int id = menuItem.getItemId();
                            switch (id) {
                                case R.id.menuXemTruoc:
                                    int tmp=dbRec.getID();
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("ID",tmp);
                                    Intent a = new Intent(YourStore.this, DetailStore.class);
                                    a.putExtra("packageID",bundle);
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
            });

            // EXTRA: individual listeners go here - if you need only a single
// listener for the entire row, put it into ActivityMain.
// This is a CLICK listener on top of the right icon (imageview2)
// (for example, here you start an intent to call phone[position])
// row listener (user clicks on any other part of the row)
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return row;
        }// getView

        // A humble POJO holding references to GUI widgets that are part of rows
// shown by the list. They have already been made and their IDs are known,
// therefore there is no need to issue 'findViewById' calls again.
        public class MyViewHolder {
            TextView tenQuanAn;
            TextView diaChiQuan;
            ImageView imgQuanAn;
            ImageView imgTuyChon;
            TextView rate;
            ImageView rating1,rating2, rating3,rating4,rating5;
        }

    }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return true;
        }


        public class TaskFindAllStore extends AsyncTask<Void, Store, Void> {

            public TaskFindAllStore() {
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    SoapObject request = new SoapObject(Configuaration.NAME_SPACE, Configuaration.METHOD_ALL_STORE);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE httpTransportSE = new HttpTransportSE(Configuaration.SERVER_URL);
                    httpTransportSE.call(Configuaration.SOAP_ACTION_ALL_STORE, envelope);
                    SoapObject listSoapObject = (SoapObject) envelope.getResponse();
                    SoapObject so = null;
                    for (int i = 0; i < listSoapObject.getPropertyCount(); i++) {
                        so = (SoapObject) listSoapObject.getProperty(i);

                        Store store = new Store();
                        store.setID(Integer.parseInt(so.getPropertyAsString("ID_CuaHang")));
                        store.setTenCuaHang(so.getPropertyAsString("TenCuaHang"));
                        store.setDiaChi(so.getPropertyAsString("DiaChi"));
                        store.setHinhAnh(StringToBitMap(so.getPropertyAsString("HinhAnh")));
                    store.setTbDanhGia(Float.parseFloat(so.getPropertyAsString("TBDanhGia")));
                        dsStore.add(store);
                    }
                } catch (Exception ex) {
                    Log.e("Lỗi", ex.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }

        private Bitmap StringToBitMap(String hinhAnh) {
            try {
                byte[] encodeByte = org.kobjects.base64.Base64.decode(hinhAnh);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
    }
