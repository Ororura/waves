package com.wavesenterprise.app.domain;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String companyName;
    private List<String> usersInCompany = new ArrayList<>();

    public Company(String companyName, List<String> usersInCompany) {
        this.companyName = companyName;
        this.usersInCompany = usersInCompany;
    }

    public Company() {}

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
