package com.example.smartgarage.repositories;

import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {


}
