package com.example.admin.foodn_test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ManageMenu extends AppCompatActivity {

    ListView lvMonAn;
    ImageButton imgThemMonAn;

    String[] tenMon = { "Món-1", "Món-2", "Món-3", "Món-4", "Món-5"};
    String[] giaMon = { "Giá món","Giá món","Giá món","Giá món", "Giá món"};

    Integer[] thumbnails = { R.drawable.menu_thumbnail, R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail, R.drawable.menu_thumbnail,
            R.drawable.menu_thumbnail};

    Integer[] tuyChonMon={R.drawable.ic_home_black_24dp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        addControls();

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
                thumbnails, tuyChonMon);
        // bind intrinsic ListView to custom adapter
        lvMonAn.setAdapter(adapter);

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản lý menu");
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }//onCreate

    private void addControls() {
        lvMonAn= (ListView) findViewById(R.id.lvMonAn);
        imgThemMonAn=findViewById(R.id.imgThemMonAn);
    }
    // react to user's selection of a row


    class CustomIconLabelAdapter extends ArrayAdapter<String> {
        Context context;
        String[] tenMon;
        String[] giaMon;
        Integer[] thumbnails;
        Integer[] tuyChonMon;

        public CustomIconLabelAdapter(Context context, int layoutToBeInflated,
                                      String[] tenMon,
                                      String[] giaMon,
                                      Integer[] thumbnails, Integer[] tuyChonMon) {
            super(context, R.layout.list_mon_an, tenMon);
            this.context = context;
            this.thumbnails = thumbnails;
            this.tenMon = tenMon;
            this.giaMon=giaMon;
            this.tuyChonMon=tuyChonMon;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View row = inflater.inflate(R.layout.list_mon_an, null);
            TextView tenMonan = (TextView) row.findViewById(R.id.txtTenMonAn);
            TextView giaMonan = (TextView) row.findViewById(R.id.txtGiaMonAn);
            ImageView imgMonan = (ImageView) row.findViewById(R.id.imgHinhMonAn);
            ImageView imgTuyChonMon=(ImageView) row.findViewById(R.id.imgTuyChonMon);
            tenMonan.setText(tenMon[position]);
            giaMonan.setText(giaMon[position]);
            imgMonan.setImageResource(thumbnails[position]);
            imgTuyChonMon.setImageResource(tuyChonMon[0]);

            imgTuyChonMon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });
            return (row);
        }
    }

    public void showDialogDeleteFood() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageMenu.this);
        builder.setTitle(R.string.delete_food);

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
        PopupMenu popup = new PopupMenu(ManageMenu.this,v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.manage_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.menuXoaMon:
                        Intent d = new Intent(ManageMenu.this, ThongTinAppUpdate.class);
                        startActivity(d);
                        break;
                    case R.id.menuChinhSuaMon:
                        Intent e = new Intent(ManageMenu.this, LoginActivity.class);
                        startActivity(e);
                        break;
                }
                return true;
            }
        });
    }
}
