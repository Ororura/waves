package com.wavesenterprise.app.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order {
    private String productName;
    private String productDesc;
    private List<String> regions = new ArrayList<>();

    public Order(String productName, String productDesc, String regions) {
        String[] regionsArray = regions.split(" ");
        this.regions = Arrays.asList(regionsArray);
        this.productName = productName;
        this.productDesc = productDesc;

    }

    public Order(){}

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

    public void setRegions(String region) {
        this.regions.add(region);
    }
}
