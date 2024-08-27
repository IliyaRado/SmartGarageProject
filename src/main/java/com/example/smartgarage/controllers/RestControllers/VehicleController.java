package com.example.smartgarage.controllers.RestControllers;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Vehicle;
import com.example.smartgarage.services.UserService;
import com.example.smartgarage.services.VehicleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicles", description = "Operations for managing vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final UserService userService;
    @Autowired
    public VehicleController(VehicleService vehicleService, UserService userService) {
        this.vehicleService = vehicleService;
        this.userService = userService;
    }
}
