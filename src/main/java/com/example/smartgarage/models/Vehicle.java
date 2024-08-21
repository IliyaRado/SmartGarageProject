package com.example.smartgarage.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "license_plate")
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4}[A-Z]{1,2}$", message = "Invalid Bulgarian license plate")
    private String licensePlate;

    @Column(name = "vin")
    @Size(min = 17, max = 17, message = "VIN must be 17 characters long")
    private String vin;

    @Column(name = "year_of_creation")
    @Min(value = 1886, message = "Year of creation must be greater than 1886")
    private String yearOfCreation;

    @Column(name = "model")
    @Size(min = 2, max = 50, message = "Model must be between 2 and 50 characters")
    private String model;

    @Column(name = "brand")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Vehicle() {
    }
    public Vehicle(String licensePlate, String vin, String yearOfCreation, String model, String brand, User user) {
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.yearOfCreation = yearOfCreation;
        this.model = model;
        this.brand=brand;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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