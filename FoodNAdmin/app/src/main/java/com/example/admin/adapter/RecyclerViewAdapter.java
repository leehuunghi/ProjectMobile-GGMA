package com.example.admin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.foodn_test.R;
import com.example.admin.model.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows 10 on 05/05/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Store> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
     public RecyclerViewAdapter(Context context, ArrayList<Store> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.itemstore, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Store store = mData.get(position);
        holder.txtTenCuaHang.setText(store.getTenCuaHang());
        holder.txtDiaChi.setText(store.getDiaChi());
        holder.imgimgAvatar.setImageBitmap(store.getHinhAnh());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTenCuaHang;
        ImageView imgimgAvatar;
        TextView txtDiaChi;
        ViewHolder(View itemView) {
            super(itemView);
            txtTenCuaHang = itemView.findViewById(R.id.txtTenCuaHang);
            imgimgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtDiaChi=itemView.findViewById(R.id.txtDiaChi);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Store getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
