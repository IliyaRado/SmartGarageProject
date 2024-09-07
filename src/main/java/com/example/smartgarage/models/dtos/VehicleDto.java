package com.example.smartgarage.models.dtos;

import com.example.smartgarage.models.Model;
import com.example.smartgarage.models.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleDto {

    private int id;

    @NotNull
    private Model model;

    @NotEmpty
    private String yearOfCreation;

    @NotEmpty
    private String licensePlate;

    private int user;

    @NotEmpty
    private String vin;



}
