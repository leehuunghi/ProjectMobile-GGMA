package com.example.admin.foodn_test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.adapter.RecyclerViewAdapter;
import com.example.admin.model.Store;

import java.util.List;

public class CustomFavAdapter extends BaseAdapter {

    Context context;
    int layoutToBeInflated;
    List<Store> dbList;

    private ItemClickListener mClickListener;
    private ItemFClickListener mFClickListener;
    private ItemUFClickListener mUFClickListener;

    public CustomFavAdapter(Context context, List<Store>
            databaseList, int resource) {
        this.context = context;
        this.dbList = databaseList;
        layoutToBeInflated = resource;
    }

    interface eventLove {

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
    public View getView(final int position, final View convertView, ViewGroup parent) {
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
            holder.imageview2 = (ImageView) row.findViewById(R.id.fl_fav);
            holder.imageview3 = (ImageView) row.findViewById(R.id.fl_unfav);
// identify this row with the POJO holder just created
            row.setTag(holder);
        } else {
// row was already created- no need to inflate and invoke findViewById
// getTag() returns the object originally stored in this view
            holder = (MyViewHolder) row.getTag();
        }
// enter(or restore) data that goes in this frame (from database 'position')
        Store dbRec = getItem(position);
        holder.textview1.setText(dbRec.getTenCuaHang());
        holder.textview2.setText(dbRec.getDiaChi());
        holder.imageview1.setImageBitmap(dbRec.getHinhAnh());
        holder.imageview2 = (ImageView) row.findViewById(R.id.fl_fav);
        holder.imageview3 = (ImageView) row.findViewById(R.id.fl_unfav);
        holder.imageview2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if  (mFClickListener != null) mFClickListener.onItemClickF(view, position);
            }
        });
        holder.imageview3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if  (mUFClickListener != null) mUFClickListener.onItemClickUF(view, position);
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
                mClickListener.onItemClick(v, position);
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
        ImageView imageview2;
        ImageView imageview3;

    }

    public void setmClickListener(ItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public void setmFClickListener(ItemFClickListener mFClickListener) {
        this.mFClickListener = mFClickListener;
    }

    public void setmUFClickListener(ItemUFClickListener mUFClickListener) {
        this.mUFClickListener = mUFClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface ItemFClickListener{
        void onItemClickF(View view, int position);
    }

    public interface ItemUFClickListener{
        void onItemClickUF(View view, int position);
    }

}// CustomMadeListener

