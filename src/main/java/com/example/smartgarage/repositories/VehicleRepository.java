package com.example.smartgarage.repositories;

import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Page<Vehicle> findAll(Specification<Vehicle> filters, Pageable pageable);
    Vehicle findById(int id);
    Vehicle findByLicensePlate(String licensePlate);
    Vehicle findByUser(User user);
    Vehicle findByVin(String vin);
}
