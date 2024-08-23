package com.example.smartgarage.repositories;

import com.example.smartgarage.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();

    User getById(int id);

    User getByUsername(String username);

    User create(User user);

    void delete(int id);

    User update(User user);

    User makeEmployee(int userId);

    User removeEmployee(int userId);


}
