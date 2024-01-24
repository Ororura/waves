package com.wavesenterprise.app.domain;

public class Distributor {
    private String companyName;
    private String suppDesc;
    private String name;
    private String phone;
    private String key;

    public Distributor(String companyName, String suppDesc, String name, String phone, String key) {
        this.companyName = companyName;
        this.suppDesc = suppDesc;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public String getPhone() { return phone; }


    public String getKey() {
        return key;
    }
}
