package com.wavesenterprise.app.domain;

public class Car {
    private String brand;
    private String model;
    private double maxSpeed;

    public Car(String brand, String model, double maxSpeed) {
        this.brand = brand;
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    public Car(){}

    public String getBrandName() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
