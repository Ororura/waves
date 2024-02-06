package com.wavesenterprise.app.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;
    private String password;
    private String role;
    public boolean blocked = false;
    private String companyName;
    private String suppDesc;
    private String phone;
    private int balance;
    private String region;
    private List<String> supplyRegions;


    public User(String login, String password, String role, boolean blocked, String companyName, String suppDesc, String phone, int balance, String region) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.blocked = blocked;
        this.companyName = companyName;
        this.suppDesc = suppDesc;
        this.phone = phone;
        this.balance = balance;
        this.region = region;
    }

    public User() {
    }

    public int getBalance() {
        return balance;
    }

    public List<String> getSupplyRegions() {
        return supplyRegions;
    }

    public void setSupplyRegions(List<String> supplyRegions) {
        this.supplyRegions = supplyRegions;
    }

    public void addSupplyRegions(String supplyRegions) {
        this.supplyRegions.add(supplyRegions);
    }


    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return this.blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSuppDesc() {
        return suppDesc;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegion() {
        return region;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSuppDesc(String suppDesc) {
        this.suppDesc = suppDesc;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
