package com.wavesenterprise.app.domain;

public class Supplier {
    private String supplierName;
    private String manufacDesc;
    private String regions;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String publicKey;

    public Supplier(String supplierName, String manufacDesc, String regions, String firstName, String lastName, String phoneNumber, String publicKey) {
        this.supplierName = supplierName;
        this.manufacDesc = manufacDesc;
        this.regions = regions;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.publicKey = publicKey;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getManufacDesc() {
        return manufacDesc;
    }

    public String getRegions() {
        return regions;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPublicKey() {
        return publicKey;
    }
}

