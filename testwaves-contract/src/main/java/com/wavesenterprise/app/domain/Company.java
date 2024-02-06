package com.wavesenterprise.app.domain;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String companyName;
    private List<String> usersInCompany = new ArrayList<>();
    private List<Product> companyShop = new ArrayList<>();

    public Company(String companyName, List<String> usersInCompany) {
        this.companyName = companyName;
        this.usersInCompany = usersInCompany;
    }

    public Company() {
    }

    public List<Product> getCompanyShop() {
        return companyShop;
    }

    public void addCompanyShop(Product companyShop) {
        this.companyShop.add(companyShop);
    }

    public void setCompanyShop(List<Product> companyShop) {
        this.companyShop = companyShop;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<String> getUsersInCompany() {
        return usersInCompany;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setUsersInCompany(List<String> usersInCompany) {
        this.usersInCompany = usersInCompany;
    }

}
