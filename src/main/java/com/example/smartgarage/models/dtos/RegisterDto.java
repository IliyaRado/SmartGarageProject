package com.example.smartgarage.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotEmpty(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotEmpty(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number must be valid")
    private String phoneNumber;
}
