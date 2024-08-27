package com.example.smartgarage.services;

import com.example.smartgarage.models.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle getVehicleById(int id);

    Vehicle addVehicle(Vehicle vehicle);

    Vehicle updateVehicle(int vehicleId, Vehicle updatedVehicle);

    void deleteVehicle(int vehicleId);

    List<Vehicle> getVehiclesByUserId(int userId);

    Vehicle getVehicleByLicensePlate(String licensePlate);

    Vehicle getVehicleByVin(String vin);

    List<Vehicle> getVehicles();
}
