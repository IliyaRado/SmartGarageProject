package com.example.smartgarage.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}