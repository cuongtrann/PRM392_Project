package com.example.project_prm392.category;

import java.io.Serializable;

public class Category implements Serializable {
    private String image;
    private String name;
    private String type;

    public Category() {
    }

    public Category(String image, String name, String type) {
        this.image = image;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
