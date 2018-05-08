package com.example.admin.model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 05/05/2018.
 */

public class Store {
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    private Integer ID;
    private String tenCuaHang;
    private String diaChi;
    private String gioMoCua;
    private String gioDongCua;
    private Bitmap hinhAnh;
    private String sdtCuaHang;
    private Marker marker;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public ArrayList<Position> getListPoint() {
        return listPoint;
    }

    public void setListPoint(ArrayList<Position> listPoint) {
        this.listPoint = listPoint;
    }

    private ArrayList<Position> listPoint = new ArrayList<>();

    public String getTenCuaHang() {
        return tenCuaHang;
    }

    public void setTenCuaHang(String tenCuaHang) {
        this.tenCuaHang = tenCuaHang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioMoCua() {
        return gioMoCua;
    }

    public void setGioMoCua(String gioMoCua) {
        this.gioMoCua = gioMoCua;
    }

    public String getGioDongCua() {
        return gioDongCua;
    }

    public void setGioDongCua(String gioDongCua) {
        this.gioDongCua = gioDongCua;
    }

    public Bitmap getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(Bitmap hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getSdtCuaHang() {
        return sdtCuaHang;
    }

    public void setSdtCuaHang(String sdtCuaHang) {
        this.sdtCuaHang = sdtCuaHang;
    }
}
