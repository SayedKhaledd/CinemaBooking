package com.example.cinemabooking.Model;

import java.io.Serializable;

public class Cinema implements Serializable {
    private String name;
    private String address;
    private String image;
    private double latitude;
    private double longitude;

    public Cinema(String name, String address, String image) {
        this.name = name;
        this.address = address;
        this.image = image;
    }


    public void setGeo(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
