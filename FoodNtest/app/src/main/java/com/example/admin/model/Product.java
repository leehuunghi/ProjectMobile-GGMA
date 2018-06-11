package com.example.admin.model;

import android.graphics.Bitmap;

/**
 * Created by Windows 10 on 11/06/2018.
 */

public class Product {
    private int id;
    private String name;
    private String price;
    private Bitmap image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
