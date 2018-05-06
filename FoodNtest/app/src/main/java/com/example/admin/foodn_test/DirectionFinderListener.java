package com.example.admin.foodn_test;

import java.util.List;

interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
