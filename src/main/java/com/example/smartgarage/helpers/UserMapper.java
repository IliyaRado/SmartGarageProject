package com.example.smartgarage.helpers;

import com.example.smartgarage.models.RegisterDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private UserService userService;

    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public User fromDto(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());

        return user;
    }


}
