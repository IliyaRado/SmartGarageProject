package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Vehicle;
import com.example.smartgarage.repositories.UserRepository;
import com.example.smartgarage.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static com.example.smartgarage.filters.VehicleSpecifications.*;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, UserService userService) {
        this.vehicleRepository = vehicleRepository;
        this.userService = userService;
    }

    @Override
    public Page<Vehicle> findAll(String modelFilter, String brandFilter, String yearOfCreationFilter, Pageable pageable) {
        Specification<Vehicle> filters = Specification.where(
                        StringUtils.isEmptyOrWhitespace(modelFilter) ? null : hasModel(modelFilter))
                .and(StringUtils.isEmptyOrWhitespace(brandFilter) ? null : hasBrand(brandFilter))
                .and(StringUtils.isEmptyOrWhitespace(yearOfCreationFilter) ? null : hasYearOfCreation(yearOfCreationFilter));
        return vehicleRepository.findAll(filters, pageable);
    }

    @Override
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }
    @Override
    public List<Vehicle> findAllVehiclesWithoutUser() {
        return vehicleRepository.findByUserIsNull();
    }
    @Override
    public Vehicle findVehicleById(int id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }

    @Override
    public Vehicle findByVin(String vin) {
        return vehicleRepository.findByVin(vin);
    }

    @Override
    public Vehicle createWithUser(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle, int id) {
        Vehicle existingVehicle = findVehicleById(id);
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setLicensePlate(vehicle.getLicensePlate());
        existingVehicle.setVin(vehicle.getVin());
        existingVehicle.setYearOfCreation(vehicle.getYearOfCreation());
        existingVehicle.setUser(vehicle.getUser());
        return vehicleRepository.save(existingVehicle);
    }

    @Override
    public void delete(int id) {
        Vehicle vehicle = findVehicleById(id);
        vehicleRepository.delete(vehicle);
    }

    @Override
    public Vehicle getVehicleByUser(int userId) {
        return vehicleRepository.findByUser(new User());
    }

    @Override
    public Vehicle createWithoutUser(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle addVehicleToUser(int vehicleId, int userId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        User user = userService.findUserById(userId);
        vehicle.setUser(user);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteWithoutUser(int id) {
        Vehicle vehicle = findVehicleById(id);
        vehicleRepository.delete(vehicle);
    }

    @Override
    public Vehicle updateWithoutUser(Vehicle vehicle, int id) {
        Vehicle existingVehicle = findVehicleById(id);
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setLicensePlate(vehicle.getLicensePlate());
        existingVehicle.setVin(vehicle.getVin());
        existingVehicle.setYearOfCreation(vehicle.getYearOfCreation());
        return vehicleRepository.save(existingVehicle);
    }
}

