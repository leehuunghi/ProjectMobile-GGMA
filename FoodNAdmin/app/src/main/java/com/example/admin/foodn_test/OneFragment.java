package com.example.admin.foodn_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.config.Configuaration;
import com.example.admin.model.Store;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment implements FragmentCallBack {

    public TextView getTxtAddess() {
        return txtAddess;
    }

    public void setTxtAddess(String txtAddess) {
        this.txtAddess.setText(txtAddess);
    }

    public TextView getTxtSDT() {
        return txtSDT;
    }

    public void setTxtSDT(String txtPrice) {
        this.txtSDT.setText(txtPrice);
    }

    TextView txtAddess, txtSDT;
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
        txtAddess = layout.findViewById(R.id.addressStore);
        txtSDT=layout.findViewById(R.id.sdt);
        return layout;
    }

    @Override
    public void MsgFromMain(String msg) {

    }

}
