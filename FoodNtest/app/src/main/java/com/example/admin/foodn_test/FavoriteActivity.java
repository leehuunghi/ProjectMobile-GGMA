package com.example.admin.foodn_test;

import android.content.ClipData;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.config.Configuaration;
import com.example.admin.model.Product;
import com.example.admin.model.Store;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, CustomFavAdapter.ItemClickListener {

    List<Store> storeList =  new ArrayList<>();
    CustomFavAdapter adapter;
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
        accName.setText(GlobalVariable.MyUser.getHoten());
        ImageView accAva = headerview.findViewById(R.id.accAva);
        accAva.setImageBitmap(GlobalVariable.MyUser.getAva());
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
        actionbar.setTitle("Quán ăn yêu thích");
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
        GetInfoStore getInfoStore = new GetInfoStore();
        adapter = new CustomFavAdapter(FavoriteActivity.this, storeList, R.layout.fav_list_gui_row);
        adapter.setmClickListener(this);
        listview.setAdapter(adapter);
        getInfoStore.execute();

    }//onCreate

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("ID",adapter.getItem(position).getID());
        Intent intentDetail = new Intent(FavoriteActivity.this, StoreDetailActivity.class);
        intentDetail.putExtras(bundle);
        startActivity(intentDetail);
    }

    class GetInfoStore extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_GET_LIST_FAV_STORE);
                request.addProperty(Configuaration.PARAMETER_IDUserF, GlobalVariable.MyUser.getId());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_GET_LIST_FAV_STORE, envelope);
                SoapObject listSoapObject= (SoapObject) envelope.getResponse();
                SoapObject so = null;
                for (int i = 0; i < listSoapObject.getPropertyCount(); i++) {
                    so = (SoapObject) listSoapObject.getProperty(i);
                    Store store = new Store();
                    so = (SoapObject) so.getProperty("CuaHang");

                    store.setID(Integer.parseInt(so.getPropertyAsString("ID_CuaHang")));
                    store.setTenCuaHang(so.getPropertyAsString("TenCuaHang"));
                    store.setDiaChi(so.getPropertyAsString("DiaChi"));
                    store.setHinhAnh(StringToBitMap(so.getPropertyAsString("HinhAnh")));
                    storeList.add(store);
                }

            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return null;
        }

        private Bitmap StringToBitMap(String hinhAnh) {
            try {
                byte[] encodeByte = Base64.decode(hinhAnh);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }
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

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
