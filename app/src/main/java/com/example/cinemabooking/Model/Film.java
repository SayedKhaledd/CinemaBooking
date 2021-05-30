package com.example.cinemabooking.Model;

import java.io.Serializable;

public class Film implements Serializable {
    private int id;
    private String name , imageURL,description;

    public Film(){}



    public Film(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
