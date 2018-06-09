package com.example.admin.foodn_test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomCommentAdapter extends BaseAdapter {
    Context context;
    int layoutToBeInflated;
    List<DATABASEcomment.DbRecord> dbList;
    public CustomCommentAdapter(DetailStore context, List<DATABASEcomment.DbRecord>
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
    public DATABASEcomment.DbRecord getItem(int position) {
        return dbList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CustomCommentAdapter.MyViewHolder holder;
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutToBeInflated, null);
            holder = new CustomCommentAdapter.MyViewHolder();
            holder.txtUserName = (TextView) row.findViewById(R.id.txtUserName);
            holder.txtComment = (TextView) row.findViewById(R.id.txtComment);
            holder.imgUser = (ImageView) row.findViewById(R.id.imgUser);
            row.setTag(holder);
        } else {
            holder = (CustomCommentAdapter.MyViewHolder) row.getTag();
        }
        DATABASEcomment.DbRecord dbRec = getItem(position);
        holder.txtUserName.setText(dbRec.tenUser);
        holder.txtComment.setText(dbRec.comment);
        holder.imgUser.setImageResource(dbRec.avt);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return row;
    }// getView
    public class MyViewHolder {
        TextView txtUserName;
        TextView txtComment;
        ImageView imgUser;
    }
}
