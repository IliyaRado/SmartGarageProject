package com.example.smartgarage.repositories;

import com.example.smartgarage.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByType(String type);
}
