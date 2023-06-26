package com.example.project_prm392.product;

import java.io.Serializable;

public class Product implements Serializable {
    private int src;
    private String mName;
    private double price;
    private int quantity;

    private double oldPrice;

    private String description;

    public Product(String mName, double price, int quantity) {
        this.mName = mName;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int src, String mName, double price, int quantity, double oldPrice, String description) {
        this.src = src;
        this.mName = mName;
        this.price = price;
        this.quantity = quantity;
        this.oldPrice = oldPrice;
        this.description = description;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }
}
