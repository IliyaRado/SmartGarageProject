package com.example.smartgarage.controllers.RestControllers;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.UserMapper;
import com.example.smartgarage.models.User;
import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;


    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;

    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        try {
            return userService.findUserById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        try {
            return userService.findUserByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        try {
            return userService.findUserByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public User getUserByPhoneNumber(@PathVariable String phoneNumber) {
        try {
            return userService.findUserByPhoneNumber(phoneNumber);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/create")
    public User createUser(@Valid @RequestBody UserDto userDto) {

        User user = userMapper.fromDto(userDto);
        userService.createUser(user);
        return user;
    }

    @PostMapping("/create/employee")
    public User createEmployee(@Valid @RequestBody UserDto userDto) {

        User user = userMapper.fromDto(userDto);
        userService.createEmployee(user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @Valid @RequestBody UserDto userDto) {
        User user = userMapper.fromDto(userDto, id);
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        User user = userService.findUserById(id);

        userService.delete(id);
    }

    @PostMapping("/send-reset-password-link")
    public void sendResetPasswordLink(@RequestParam String email) {
        userService.sendPasswordResetLink(email);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        userService.resetPassword(token, newPassword);
    }


}
