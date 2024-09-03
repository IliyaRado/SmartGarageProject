package com.example.smartgarage.helpers;

import com.example.smartgarage.models.Vehicle;
import com.example.smartgarage.models.dtos.VehicleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public static VehicleDto toDto(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setModel(vehicle.getModel());
        vehicleDto.setYearOfCreation(vehicle.getYearOfCreation());
        vehicleDto.setLicensePlate(vehicle.getLicensePlate());
        vehicleDto.setUserId(vehicle.getUser().getId());
        vehicleDto.setVin(vehicle.getVin());
        return vehicleDto;
    }

    public static Vehicle fromDto(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setYearOfCreation(vehicleDto.getYearOfCreation());
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setVin(vehicleDto.getVin());
        return vehicle;
    }
}
