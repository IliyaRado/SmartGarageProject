package com.example.smartgarage.controllers.RestControllers;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.UserMapper;
import com.example.smartgarage.helpers.VehicleMapper;
import com.example.smartgarage.models.Vehicle;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.services.UserService;
import com.example.smartgarage.services.VehicleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicles", description = "Operations for managing vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @Autowired
    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAll();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable int id) {
        try {
            return vehicleService.findVehicleById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public Vehicle getVehicleByOwner(@PathVariable int userId) {
        try {
            return vehicleService.getVehicleByUser(userId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/licensePlate/{licensePlate}")
    public Vehicle getVehicleByLicensePlate(@PathVariable String licensePlate) {
        try {
            return vehicleService.findByLicensePlate(licensePlate);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/vin/{vin}")
    public Vehicle getVehicleByVin(@PathVariable String vin) {
        try {
            return vehicleService.findByVin(vin);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("")
    public Vehicle createVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleMapper.fromDto(vehicleDto);
        return vehicleService.save(vehicle);

    }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable int id, @RequestBody VehicleDto vehicleDto) {

        Vehicle vehicle = vehicleMapper.fromDto(vehicleDto);

        return vehicleService.update(vehicle, id);
    }


}
