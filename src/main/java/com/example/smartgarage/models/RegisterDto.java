package com.example.smartgarage.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto extends LoginDto {

    @NotEmpty
    private String passwordConfirm;

    @NotEmpty
    private String email;

    @NotEmpty
    private String phoneNumber;
}
