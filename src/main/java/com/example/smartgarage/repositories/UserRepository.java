package com.example.smartgarage.repositories;

import com.example.smartgarage.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findAll(Specification<User> filters, Pageable pageable);
    User findById(int id);
    Optional<User> findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    User findByUsername(String username);

}