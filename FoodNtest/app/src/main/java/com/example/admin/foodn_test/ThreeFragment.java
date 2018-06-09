package com.example.admin.foodn_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

public class ThreeFragment extends Fragment implements FragmentCallBack {
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
    public ThreeFragment() {
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
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_three, container, false);
        txtAddess = layout.findViewById(R.id.address);
        txtPrice= layout.findViewById(R.id.price);
        return layout;
    }

    @Override
    public void MsgFromMain(String msg) {

    }
}
