package com.example.smartgarage.services;

import com.example.smartgarage.models.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getById(int id);

    User getByUsername(String username);

    User create(User user);

    void delete(int id, User user);

    User update(User user, User currentUser);

    User makeEmployee(int userId);

    User removeEmployee(int userId);
}
