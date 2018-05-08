package com.example.admin.foodn_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ManageMenu extends AppCompatActivity {

    ListView lvMonAn;
    ImageButton imgThemMonAn;

    String[] tenMon = { "Món-1", "Món-2", "Món-3", "Món-4", "Món-5"};
    String[] giaMon = { "Gía món","Gía món","Gía món","Gía món", "Gía món"};

    Integer[] thumbnails = { R.drawable.menu_thumbnail, R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail, R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        lvMonAn= (ListView) findViewById(R.id.lvMonAn);

        imgThemMonAn=findViewById(R.id.imgThemMonAn);

        imgThemMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intAddFood=new Intent(ManageMenu.this,AddingFood.class);
                startActivity(intAddFood);
            }
        });

        lvMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        ManageMenu.CustomIconLabelAdapter adapter = new ManageMenu.CustomIconLabelAdapter(
                this,
                R.layout.list_mon_an,
                tenMon,giaMon,
                thumbnails);
        // bind intrinsic ListView to custom adapter
        lvMonAn.setAdapter(adapter);
    }//onCreate
    // react to user's selection of a row


    class CustomIconLabelAdapter extends ArrayAdapter<String> {
        Context context;
        String[] tenMon;
        String[] giaMon;
        Integer[] thumbnails;

        public CustomIconLabelAdapter(Context context, int layoutToBeInflated,
                                      String[] tenMon,
                                      String[] giaMon,
                                      Integer[] thumbnails) {
            super(context, R.layout.list_mon_an, tenMon);
            this.context = context;
            this.thumbnails = thumbnails;
            this.tenMon = tenMon;
            this.giaMon=giaMon;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View row = inflater.inflate(R.layout.list_mon_an, null);
            TextView tenMonan = (TextView) row.findViewById(R.id.txtTenMonAn);
            TextView giaMonan = (TextView) row.findViewById(R.id.txtGiaMonAn);
            ImageView imgMonan = (ImageView) row.findViewById(R.id.imgHinhMonAn);
            tenMonan.setText(tenMon[position]);
            giaMonan.setText(giaMon[position]);
            imgMonan.setImageResource(thumbnails[position]);
            return (row);
        }
    }
}
