package com.example.smartgarage.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceDto {

    private int id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Min(value = 0, message = "Price must be a non-negative number")
    private double price;



}
