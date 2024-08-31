package com.example.smartgarage.repositories;

import com.example.smartgarage.models.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepository extends JpaRepository<Service, Integer> {
    Service findByName(String name);
    Page<Service> findAll(Specification<Service> filters, Pageable pageable);
}
