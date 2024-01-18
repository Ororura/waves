package com.wavesenterprise.app.domain;

public class Distributor {
    private String companyName;
    private String suppDesc;
    private String firstName;
    private String lastName;
    private String phone;
    private String key;

    public Distributor(String companyName, String suppDesc, String firstName, String lastName, String phone, String key) {
        this.companyName = companyName;
        this.suppDesc = suppDesc;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.key = key;
    }

    public Distributor(){}

    public String getCompanyName() {
        return companyName;
    }

    public String getSuppDesc() {
        return suppDesc;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getKey() {
        return key;
    }
}
