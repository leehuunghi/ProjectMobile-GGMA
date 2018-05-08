package com.example.admin.foodn_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class TwoFragment extends Fragment {

    DATABASE database_records;
    private List sections = new ArrayList();
    private static int TYPE_SECTION_HEADER = 1;
    StoreDetailActivity storeDetailActivity;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeDetailActivity = (StoreDetailActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_two, container, false);
        ListView listview = (ListView) layout.findViewById(R.id.lvMenu);
        List database = new DATABASE().dbList;
        CustomBaseAdapter adapter = new CustomBaseAdapter(storeDetailActivity, database, R.layout.list_gui_row);
        listview.setAdapter(adapter);

        return layout;
    }
}
