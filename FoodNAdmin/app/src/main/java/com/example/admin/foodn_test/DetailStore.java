package com.example.admin.foodn_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.admin.config.Configuaration;
import com.example.admin.model.Store;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class DetailStore extends AppCompatActivity {

    OneFragment oneFragment;
    Store store = new Store();

    public class TaskFindStoreByID extends AsyncTask<Void, Store, Void> {

        public TaskFindStoreByID() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE, Configuaration.METHOD_STORE_ID);
                request.addProperty(Configuaration.PARAMETER_ID,GlobalVariable.IDStore);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE = new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_STORE_ID, envelope);
                SoapObject so = (SoapObject) envelope.getResponse();
                so = (SoapObject) so.getProperty("CuaHang");
                store.setTenCuaHang(so.getPropertyAsString("TenCuaHang"));
                store.setDiaChi(so.getPropertyAsString("DiaChi"));
                store.setSdtCuaHang(so.getPropertyAsString("SDT_CuaHang"));
                store.setTbDanhGia(Float.parseFloat(so.getPropertyAsString("TBDanhGia")));
                store.setGioMoCua(so.getPropertyAsString("GioMoCua"));
                store.setGioDongCua(so.getPropertyAsString("GioDongCua"));
            } catch (Exception ex) {
                Log.e("Lỗi", ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            getSupportActionBar().setTitle(store.getTenCuaHang());
//            oneFragment.setTxtAddess(store.getDiaChi());
//            oneFragment.setTxtSDT(store.getSdtCuaHang());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);

        Intent intent=getIntent();
        Bundle packageFromCaller= intent.getBundleExtra("packageID");
        GlobalVariable.IDStore= packageFromCaller.getInt("ID");

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TaskFindStoreByID taskFindAllStore = new TaskFindStoreByID();
        taskFindAllStore.execute();
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
        DetailStore.ViewPagerAdapter adapter = new DetailStore.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "THÔNG TIN CHUNG");
        adapter.addFragment(new TwoFragment(), "MENU");
        adapter.addFragment(new ThreeFragment(), "BÌNH LUẬN");
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
