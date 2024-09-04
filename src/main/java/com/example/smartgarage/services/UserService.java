package com.example.smartgarage.services;

import com.example.smartgarage.models.Role;
import com.example.smartgarage.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<User> getAll();
    Page<User> findAll(String usernameFilter, String emailFilter, String phoneNumberFilter, Pageable pageable);
    User findUserById(int id);
    User findUserByEmail(String email);
    User findUserByUsername(String username);
    User findUserByPhoneNumber(String phoneNumber);
    User createUser(User user);
    User createEmployee(User user);
    User update(User user);
    void delete(int id);
    void sendPasswordResetLink(String email);
    void resetPassword(String token, String password);
}
