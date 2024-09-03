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
    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
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
    public Vehicle findVehicleById(int id) {
        Vehicle vehicle = vehicleRepository.findById(id);
        if (vehicle == null) {
            throw new EntityNotFoundException("Vehicle", id);
        }
        return vehicle;
    }

    @Override
    public Vehicle findByLicensePlate(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (vehicle == null) {
            throw new EntityNotFoundException("Vehicle","License plate", licensePlate);
        }
        return vehicle;
    }

    @Override
    public Vehicle findByVin(String vin) {
        Vehicle vehicle = vehicleRepository.findByVin(vin);
        if (vehicle == null) {
            throw new EntityNotFoundException("Vehicle","Vin", vin);
        }
        return vehicle;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle, int id) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void delete(int id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Vehicle getVehicleByUser(int userId) {
        Vehicle vehicle = vehicleRepository.findById(userId);
        if (vehicle == null) {
            throw new EntityNotFoundException("Vehicle", userId);
        }
        return vehicle;
    }


}
