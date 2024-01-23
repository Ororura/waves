package com.wavesenterprise.app.domain;

public class BlockUser {
    private String userName;
    private boolean status;


    public BlockUser(String userName, boolean status) {
        this.userName = userName;
        this.status = status;
    }

    public BlockUser(){}

    public String getUserName() {
        return userName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
