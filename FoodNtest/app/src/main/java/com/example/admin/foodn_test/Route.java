package com.example.admin.foodn_test;

import com.google.android.gms.maps.model.LatLng;

import java.time.Duration;
import java.util.List;

class Route {
    public Distance distance;
    public com.example.admin.foodn_test.Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
