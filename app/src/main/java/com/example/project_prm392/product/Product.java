package com.example.project_prm392.product;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private String category;
    private String description;
    private String image;
    private double unitPrice;
    private int unitsInStock;

    public Product() {
    }

    public Product(String name, String category, String description, String image, double unitPrice, int unitsInStock) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.image = image;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }
}
