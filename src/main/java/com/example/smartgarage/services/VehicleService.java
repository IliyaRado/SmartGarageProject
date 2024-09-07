package com.example.smartgarage.services;

import com.example.smartgarage.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {
    Page<Vehicle> findAll(String modelFilter, String brandFilter, String yearOfCreationFilter, Pageable pageable);
    List<Vehicle> getAll();
    List<Vehicle> findAllVehiclesWithoutUser();
    Vehicle findVehicleById(int id);
    Vehicle getVehicleByUser(int userId);
    Vehicle findByLicensePlate(String licensePlate);
    Vehicle findByVin(String vin);
    Vehicle createWithUser(Vehicle vehicle);
    Vehicle update(Vehicle vehicle, int id);
    void delete(int id);
    Vehicle createWithoutUser(Vehicle vehicle);
    Vehicle addVehicleToUser(int vehicleId, int userId);
    void deleteWithoutUser(int id);
    Vehicle updateWithoutUser(Vehicle vehicle, int id);
}
