package com.wavesenterprise.app.domain;

public class Supplier {
    private String supplierName;
    private String manufacDesc;
    private String regions;
    private String name;
    private String phoneNumber;
    private String publicKey;

    public Supplier(String supplierName, String manufacDesc, String regions, String phoneNumber, String name, String publicKey) {
        this.supplierName = supplierName;
        this.manufacDesc = manufacDesc;
        this.regions = regions;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.publicKey = publicKey;
    }

    public Supplier() {}

    public String getSupplierName() {
        return supplierName;
    }

    public String getManufacDesc() {
        return manufacDesc;
    }

    public String getRegions() {
        return regions;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPublicKey() {
        return publicKey;
    }
}

