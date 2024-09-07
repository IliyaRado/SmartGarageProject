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
    @GetMapping("/withoutUser")
    public List<Vehicle> getVehiclesWithoutUser() {
        return vehicleService.findAllVehiclesWithoutUser();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable int id) {
        return vehicleService.findVehicleById(id);
    }


    @GetMapping("/user/{userId}")
    public Vehicle getVehicleByOwner(@PathVariable int userId) {
        return vehicleService.getVehicleByUser(userId);
    }

    @GetMapping("/licensePlate/{licensePlate}")
    public Vehicle getVehicleByLicensePlate(@PathVariable String licensePlate) {
        return vehicleService.findByLicensePlate(licensePlate);
    }

    @GetMapping("/vin/{vin}")
    public Vehicle getVehicleByVin(@PathVariable String vin) {
        return vehicleService.findByVin(vin);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle createVehicleWithUser(@Valid @RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleMapper.fromDtoWithUser(vehicleDto);
        return vehicleService.createWithUser(vehicle);
    }

    @PutMapping("/{id}")
    public Vehicle updateVehicleWithUser(@PathVariable int id, @Valid @RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleMapper.fromDtoWithUser(vehicleDto);
        return vehicleService.update(vehicle, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable int id) {
        vehicleService.delete(id);
    }

    @PostMapping("/withoutUser")
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle createVehicleWithoutUser(@Valid @RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleMapper.fromDtoWithoutUser(vehicleDto);
        return vehicleService.createWithoutUser(vehicle);
    }

    @PutMapping("/{vehicleId}/addUser/{userId}")
    public Vehicle addVehicleToUser(@PathVariable int vehicleId, @PathVariable int userId) {
        return vehicleService.addVehicleToUser(vehicleId, userId);
    }

    @DeleteMapping("/withoutUser/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicleWithoutUser(@PathVariable int id) {
        vehicleService.deleteWithoutUser(id);
    }

    @PutMapping("/withoutUser/{id}")
    public Vehicle updateVehicleWithoutUser(@PathVariable int id, @Valid @RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleMapper.fromDtoWithoutUser(vehicleDto);
        return vehicleService.updateWithoutUser(vehicle, id);
    }
}
