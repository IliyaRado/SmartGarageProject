package com.example.smartgarage.repositories;

import com.example.smartgarage.models.Model;
import com.example.smartgarage.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Integer> {


}
