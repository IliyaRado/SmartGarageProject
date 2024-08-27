package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.DuplicateEntityException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getUsers() {
        return userRepository.getAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public User create(User user) {

        return userRepository.save(user);
    }

    @Override
    public void delete(int id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        if (!(existingUser.equals(user))) {
            throw new AuthorizationException("Only the user themselves can delete the user.");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user, User currentUser) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User", user.getId()));
        if (!(existingUser.equals(currentUser))) {
            throw new AuthorizationException("Only admins or the user themselves can update the user.");
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
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
