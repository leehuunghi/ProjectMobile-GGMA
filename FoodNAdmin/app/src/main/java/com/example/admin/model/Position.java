package com.example.admin.model;

/**
 * Created by Windows 10 on 06/05/2018.
 */

public class Position {
    private  double v;
    private  double v1;
    public double getV() {
        return v;
    }

    public Position(double v, double v1) {
        this.v = v;
        this.v1 = v1;
    }

    public void setV(double v) {
        this.v = v;
    }

    public double getV1() {
        return v1;
    }

    public void setV1(double v1) {
        this.v1 = v1;
    }
}
