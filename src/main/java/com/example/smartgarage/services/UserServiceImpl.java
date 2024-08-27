package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.DuplicateEntityException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User create(User user) {
        boolean duplicateExists = true;

        try {
            userRepository.getByUsername(user.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
        return userRepository.create(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public void delete(int id, User user) {
//        User existingUser = userRepository.getById(id);
//        if (!(user.isAdmin() || existingUser.equals(user))) {
//            throw new AuthorizationException("Only admins or the user themselves can delete the user.");
//        }
    }

    @Override
    public User update(User user, User currentUser) {
        return null;
    }

    @Override
    public void blockUser(int userId, User currentUser) {

    }

    @Override
    public void unblockUser(int userId, User currentUser) {

    }

    @Override
    public User makeEmployee(int userId) {
        return null;
    }

    @Override
    public User removeEmployee(int userId) {
        return null;
    }
}
