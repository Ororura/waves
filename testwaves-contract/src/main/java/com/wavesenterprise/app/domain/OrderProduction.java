package com.wavesenterprise.app.domain;


public class OrderProduction {
    private int id;
    private int amount;
    private String customer;

    public OrderProduction(int id, int amount, String customer) {
        this.id = id;
        this.amount = amount;
        this.customer = customer;
    }

    public OrderProduction() {
    }

    public int getAmount() {
        return amount;
    }

    public String getCustomer() {
        return customer;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
