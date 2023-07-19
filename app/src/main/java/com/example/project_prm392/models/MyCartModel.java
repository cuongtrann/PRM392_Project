package com.example.project_prm392.models;

public class MyCartModel {
    String name;
    int price;
    String color;
    int size;
    int quantity;
    String image;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }

    public MyCartModel(String name, int price, String color, int size, int quantity, String image) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.image = image;
    }
}
