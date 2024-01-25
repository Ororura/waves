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
    private String name;
    private String phone;
    private String key;
    private List<String> regions = new ArrayList<>();


    public User(String login, String password, String role, boolean blocked, String companyName, String suppDesc, String name, String phone, String key) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.blocked = blocked;
        this.companyName = companyName;
        this.suppDesc = suppDesc;
        this.name = name;
        this.phone = phone;
        this.key = key;
    }
    public User() {}

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

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getKey() {
        return key;
    }

    public List<String> getRegions() {
        return regions;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }
}
