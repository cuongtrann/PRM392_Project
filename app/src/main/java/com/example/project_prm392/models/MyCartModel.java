package com.example.project_prm392.models;

public class MyCartModel {
    String productName;
    int price;
    String size;
    int quantity;
    String image;

    public void setName(String name) {
        this.productName = name;
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

    public MyCartModel(String name, int price, String size, int quantity, String image) {
        this.productName = name;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
        this.image = image;
    }
}
