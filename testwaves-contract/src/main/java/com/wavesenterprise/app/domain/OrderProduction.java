package com.wavesenterprise.app.domain;

import static com.wavesenterprise.app.api.IContract.Keys.*;

public class OrderProduction {
    private int id;
    private int amount;
    private String date;
    private int price;
    private String customer;
    private String shop;
    private String status = STATUS_PREPARING;

    public OrderProduction(int id, int amount, String date, int price, String customer, String shop, String status) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.price = price;
        this.customer = customer;
        this.shop = shop;
        this.status = status;
    }

    public OrderProduction() {
    }

    public String getShop() {
        return shop;
    }

    public int getAmount() {
        return amount;
    }

    public String getCustomer() {
        return customer;
    }

    public String getStatus() {
        return status;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
