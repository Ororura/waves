package com.wavesenterprise.app.domain;

import static com.wavesenterprise.app.api.IContract.Keys.*;

public class OrderProduction {
    private int id;
    private String productName;
    private int amount;
    private String date;
    private int price;
    private String customer;
    private String company;
    private boolean preOrder;
    private String distributor;
    private String status = STATUS_PREPARING;

    public OrderProduction(int id, int amount, String date, int price, String customer, String company, boolean preOrder) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.price = price;
        this.customer = customer;
        this.company = company;
        this.preOrder = preOrder;
    }

    public OrderProduction() {
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public boolean isPreOrder() {
        return preOrder;
    }

    public void setPreOrder(boolean preOrder) {
        this.preOrder = preOrder;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompany() {
        return company;
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

    public void setCompany(String company) {
        this.company = company;
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
