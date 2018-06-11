package com.example.admin.foodn_test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.admin.config.Configuaration;
import com.example.admin.model.Position;
import com.example.admin.model.Store;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class StoreDetailActivity extends AppCompatActivity {
    OneFragment oneFragment;
    TwoFragment twoFragment;
    ThreeFragment threeFragment;
    ImageButton fav;
    ImageButton unfav;
    Store store = new Store();
    int ID;
    class GetInfo extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getSupportActionBar().setTitle(store.getTenCuaHang());
            oneFragment.setTxtAddess(store.getDiaChi());

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_GET_STORE_BY_ID);
                request.addProperty(Configuaration.PARAMETER_ID, ID);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_GET_STORE_BY_ID, envelope);
                SoapObject so= (SoapObject) envelope.getResponse();
                so = (SoapObject) so.getProperty("CuaHang");
                store.setID(Integer.parseInt(so.getPropertyAsString("ID_CuaHang")));
                store.setTenCuaHang(so.getPropertyAsString("TenCuaHang"));
                store.setDiaChi(so.getPropertyAsString("DiaChi"));
                store.setHinhAnh(StringToBitMap(so.getPropertyAsString("HinhAnh")));
            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getInt("ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        GetInfo getInfo = new GetInfo();
        getInfo.execute();

        fav = findViewById(R.id.fav);
        unfav = findViewById(R.id.unfav);
        fav.setVisibility(View.INVISIBLE);
        unfav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fav.setVisibility(View.VISIBLE);
                unfav.setVisibility(View.INVISIBLE);
            }
        });
        fav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fav.setVisibility(View.INVISIBLE);
                unfav.setVisibility(View.VISIBLE);
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        adapter.addFragment(oneFragment, "THÔNG TIN");
        adapter.addFragment(twoFragment, "MENU");
        adapter.addFragment(threeFragment, "BÌNH LUẬN");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}