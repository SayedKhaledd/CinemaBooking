package com.example.cinemabooking.Model;

import java.io.Serializable;

public class Film implements Serializable {
    private String name , imageUML,description;

    public Film(){}



    public Film(String name, String imageUML) {
        this.name = name;
        this.imageUML = imageUML;
    }

    public String getImageUML() {
        return imageUML;
    }

    public void setImageUML(String imageUML) {
        this.imageUML = imageUML;
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
