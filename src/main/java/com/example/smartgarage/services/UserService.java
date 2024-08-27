package com.example.smartgarage.services;

import com.example.smartgarage.models.Role;
import com.example.smartgarage.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


//    Page<User> findAll(String usernameFilter, String emailFilter, String firstNameFilter, Pageable pageable);
    List<User> getAll();
    User findUserById(int id);
    User findUserByEmail(String email);
    User findUserByUsername(String username);
    User findUserByPhoneNumber(String phoneNumber);
    boolean authenticateUser(String rawPassword, String storeHashedPassword);

    User createUser(User user);
    User updateUser(User user);
    void deleteUser(int id);

}
