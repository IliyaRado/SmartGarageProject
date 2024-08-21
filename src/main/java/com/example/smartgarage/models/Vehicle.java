package com.example.smartgarage.models;

public class Vehicle {
    private int id;
    private String licensePlate;
    private String vin;
    private String yearOfCreation;
    private String model;
    private String brand;
    private String userId;

    public Vehicle() {
    }
    public Vehicle(String licensePlate, String vin, String yearOfCreation, String model, String brand, String userId) {
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.yearOfCreation = yearOfCreation;
        this.model = model;
        this.brand=brand;
        this.userId = userId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(String yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}