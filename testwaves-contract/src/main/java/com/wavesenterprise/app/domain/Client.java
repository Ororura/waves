package com.wavesenterprise.app.domain;

public class Client {
    private String name;
    private String email;
    private String key;

    public Client(String name, String email, String key) {
        this.name = name;
        this.email = email;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getKey() {
        return key;
    }
}
