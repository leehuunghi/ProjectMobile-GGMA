package com.example.admin.foodn_test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.model.Product;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    int layoutToBeInflated;
    List<Product> dbList;
    public CustomBaseAdapter(StoreDetailActivity context, List<Product>
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
    public Product getItem(int position) {
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
            holder.textview1 = (TextView) row.findViewById(R.id.rowTextView1);
            holder.textview2 = (TextView) row.findViewById(R.id.rowTextView2);
            holder.imageview1 = (ImageView) row.findViewById(R.id.rowImageView1);
// identify this row with the POJO holder just created
            row.setTag(holder);
        } else {
// row was already created- no need to inflate and invoke findViewById
// getTag() returns the object originally stored in this view
            holder = (MyViewHolder) row.getTag();
        }
// enter(or restore) data that goes in this frame (from database 'position')
        Product dbRec = (Product) getItem(position);
        holder.textview1.setText(dbRec.getName());
        holder.textview2.setText(dbRec.getPrice());
        holder.imageview1.setImageBitmap(dbRec.getImage());
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
        TextView textview1;
        TextView textview2;
        ImageView imageview1;
    }
}// CustomMadeListener

