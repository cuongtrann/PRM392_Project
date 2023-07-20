package com.example.project_prm392.models;

public class Order {

    private String userId;

    private String orderDate;

    public Order(String userId, String orderDate) {
        this.userId = userId;
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
