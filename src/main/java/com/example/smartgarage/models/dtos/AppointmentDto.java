package com.example.smartgarage.models.dtos;

import com.example.smartgarage.models.Service;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppointmentDto {

    private int id;

    @NotEmpty
    private String username;

    @NotEmpty(message = "Email cannot be empty!")
    @Email
    private String email;

    @NotEmpty
    private String yearOfCreation;

    @NotEmpty
    private String licensePlate;

    @NotEmpty
    private String vin;

    @NotNull
    private int modelId;

    @NotEmpty(message = "At least one service must be provided.")
    private List<Integer> serviceIds;

}
