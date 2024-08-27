package com.example.smartgarage.services;

import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Vehicle;
import com.example.smartgarage.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    @Override
    public Vehicle getVehicleById(int id) {
        return vehicleRepository.getById(id);
    }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.create(vehicle);
    }

    @Override
    public Vehicle updateVehicle(int vehicleId, Vehicle updatedVehicle) {
        return vehicleRepository.update(updatedVehicle);
    }

    @Override
    public void deleteVehicle(int vehicleId) {
        vehicleRepository.delete(vehicleId);
    }

    @Override
    public List<Vehicle> getVehiclesByUserId(int userId) {
        return vehicleRepository.getByUser(new User());
    }

    @Override
    public Vehicle getVehicleByLicensePlate(String licensePlate) {
        return vehicleRepository.getByLicensePlate(licensePlate);
    }

    @Override
    public Vehicle getVehicleByVin(String vin) {
        return vehicleRepository.getByVin(vin);
    }

    @Override
    public List<Vehicle> getVehicles() {
        return vehicleRepository.getAll();
    }

}
