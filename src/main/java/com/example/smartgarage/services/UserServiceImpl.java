package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.DuplicateEntityException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.User;
import com.example.smartgarage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public Page<User> findAll(String usernameFilter, String emailFilter, String firstNameFilter, Pageable pageable) {
//        return userRepository.findAll(usernameFilter, emailFilter, firstNameFilter, pageable);
//    }


    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User", "email", email));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("User", "phone number", phoneNumber));
    }



    @Override
    public boolean authenticateUser(String rawPassword, String storeHashedPassword) {
        return false;
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("User", user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User", id);
        }
        userRepository.deleteById(id);
    }
}