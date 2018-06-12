package com.example.admin.foodn_test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.model.Store;

import java.util.zip.Inflater;

public class OneFragment extends Fragment implements FragmentCallBack {
    public TextView getTxtAddess() {
        return txtAddess;
    }

    public void setTxtAddess(String txtAddess) {
        this.txtAddess.setText(txtAddess);
    }

    public TextView getTxtPrice() {
        return txtPrice;
    }

    public void setTxtPrice(String txtPrice) {
        this.txtPrice.setText(txtPrice);
    }

    TextView txtAddess, txtPrice;
    ImageButton location;
    ImageView img;

    public void setTxtAddess(TextView txtAddess) {
        this.txtAddess = txtAddess;
    }

    public void setTxtPrice(TextView txtPrice) {
        this.txtPrice = txtPrice;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img.setImageBitmap(img);;
    }

    Store store;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_one, container, false);
        img = layout.findViewById(R.id.img);
        txtAddess = layout.findViewById(R.id.address);
        txtPrice= layout.findViewById(R.id.price);
        location = layout.findViewById(R.id.btnPositon);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StoreMapActivity.class);
                Bundle add = new Bundle();
                add.putString("add", txtAddess.getText().toString());
                intent.putExtras(add);
                startActivity(intent);
            }
        });
        return layout;
    }

    @Override
    public void MsgFromMain(String msg) {

    }
}
