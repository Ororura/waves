package com.wavesenterprise.app.domain;

import java.util.ArrayList;
import java.util.List;

import static com.wavesenterprise.app.api.IContract.Keys.ON_CHECK;

public class Product {
    private String productName;
    private String productDesc;
    private String status = ON_CHECK;
    private int maxCount;
    private int minCount;
    private List<String> regions = new ArrayList<>();
    private List<String> distributors = new ArrayList<>();

    public Product(String productName, String productDesc) {
        this.productName = productName;
        this.productDesc = productDesc;
    }

    public Product() {
    }

    public List<String> getDistributors() {
        return distributors;
    }

    public void addDistributors(String distributors) {
        this.distributors.add(distributors);
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getMinCount() {
        return minCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
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
