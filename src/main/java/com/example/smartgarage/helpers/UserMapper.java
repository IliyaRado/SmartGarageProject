package com.example.smartgarage.helpers;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.RegisterDto;
import com.example.smartgarage.models.User;
import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.repositories.UserRepository;
import com.example.smartgarage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserRepository repository;

    @Autowired
    public UserMapper(UserRepository repository) {
        this.repository = repository;
    }

    public User fromDto(UserDto dto, int id) {
        User repositoryUser = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));  // Handle Optional here

        User user = fromDto(dto);
        user.setUsername(repositoryUser.getUsername());
        user.setPhoneNumber(repositoryUser.getPhoneNumber());
        user.setRole(repositoryUser.getRole());
        user.setId(id);

        return user;
    }

    public User fromDto(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

}
