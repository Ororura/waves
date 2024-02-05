package com.wavesenterprise.app.domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String productName;
    private String productDesc;
    private List<String> regions = new ArrayList<>();

    public Product(String productName, String productDesc) {
        this.productName = productName;
        this.productDesc = productDesc;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
