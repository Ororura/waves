package com.wavesenterprise.app.domain;


public class OrderProduction {
    private int id;
    private String productName;
    private int amount;
    private String status;

    public OrderProduction(String productName, int amount, String status) {
        this.productName = productName;
        this.amount = amount;
        this.status = status;
    }

    public OrderProduction() {
    }

    public String getProductName() {
        return productName;
    }

    public int getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
