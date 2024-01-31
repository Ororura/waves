package com.wavesenterprise.app.domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String productName;
    private String productDesc;
    private String owner;
    private List<String> regions = new ArrayList<>();

    public Product(String productName, String productDesc, String owner) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.owner = owner;
    }

    public Product(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setRegions(List<String> region) {
        this.regions = region;
    }
}
