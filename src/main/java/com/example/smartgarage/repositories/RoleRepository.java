package com.example.smartgarage.repositories;

import com.example.smartgarage.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByType(String type);
    Role findById(int id);
}
