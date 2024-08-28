package com.example.smartgarage.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public LoginDto() {

    }
}
