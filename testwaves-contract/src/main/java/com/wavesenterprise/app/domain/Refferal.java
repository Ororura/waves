package com.wavesenterprise.app.domain;

public class Refferal {
    private String name;
    private String userLogin;
    private int discount;

    public Refferal(String name, String userLogin, int discount) {
        this.name = name;
        this.userLogin = userLogin;
        this.discount = discount;
    }
     public Refferal() {}

    public String getName() {
        return name;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setName(String name) {
        this.name = name;
    }
}
