package com.wavesenterprise.app.domain;

public class Car {
    private String brandName;
    private String model;
    private double maxSpeed;

    public Car(String brandName, String model, double maxSpeed) {
        this.brandName = brandName;
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    public Car(){}

    public String getBrandName() {
        return brandName;
    }

    public String getModel() {
        return model;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
