package com.example.project_prm392.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {

    String uuid;
    String productName;
    int price;
    String size;
    int quantity;
    String image;

    public MyCartModel() {
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUuid() {
        return uuid;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }

    public MyCartModel(String uuid, String productName, int price, String size, int quantity, String image) {
        this.uuid = uuid;
        this.productName = productName;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
        this.image = image;
    }
}
