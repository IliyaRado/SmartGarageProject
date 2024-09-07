package com.example.smartgarage.helpers;

import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Vehicle;
import com.example.smartgarage.models.dtos.VehicleDto;
import com.example.smartgarage.services.UserService;
import com.example.smartgarage.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    private static UserService userService;

    @Autowired
    public VehicleMapper(UserService userService) {
        VehicleMapper.userService = userService;
    }


    public VehicleDto toDto(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setModel(vehicle.getModel());
        vehicleDto.setYearOfCreation(vehicle.getYearOfCreation());
        vehicleDto.setLicensePlate(vehicle.getLicensePlate());
        vehicleDto.setUser(vehicle.getUser().getId());
        vehicleDto.setVin(vehicle.getVin());
        return vehicleDto;
    }

    public Vehicle fromDtoWithUser(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        User user = userService.findUserById(vehicleDto.getUser());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setYearOfCreation(vehicleDto.getYearOfCreation());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setVin(vehicleDto.getVin());
        vehicle.setUser(user);
        return vehicle;
    }

    public Vehicle fromDtoWithoutUser(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setYearOfCreation(vehicleDto.getYearOfCreation());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setVin(vehicleDto.getVin());
        return vehicle;
    }
}