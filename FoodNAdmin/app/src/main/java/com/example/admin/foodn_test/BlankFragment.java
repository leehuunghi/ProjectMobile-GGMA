package com.example.admin.foodn_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;



public class BlankFragment extends Fragment {

    DetailStore storeDetailActivity;

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeDetailActivity = (DetailStore) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_three, container, false);
        ListView listview = (ListView) layout.findViewById(R.id.lvComment);
        List database = new DATABASEcomment().dbList;
        CustomBaseAdapter adapter = new CustomBaseAdapter(storeDetailActivity, database, R.layout.list_comment);
        listview.setAdapter(adapter);

        return layout;
    }
}
