package com.example.admin.foodn_test;

import android.location.Location;

import com.example.admin.model.Store;
import com.example.admin.model.User;
import com.google.android.gms.maps.GoogleMap;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows 10 on 09/06/2018.
 */

public final class GlobalVariable {
    public static User MyUser;
    public static ArrayList<Store> listStore = new ArrayList<Store>();
    public static GoogleMap mMap;
    public static ArrayList<Direction> listDirection = new ArrayList<>();
    public static Location myLocationDevide;
}
