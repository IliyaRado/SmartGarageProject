package com.example.smartgarage.helpers;

import com.example.smartgarage.models.Service;
import com.example.smartgarage.models.dtos.ServiceDto;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public Service fromDto(ServiceDto dto) {
        Service service = new Service();
        service.setName(dto.getName());
        service.setPrice(dto.getPrice());
        return service;
    }

    public ServiceDto toDto(Service service) {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setName(service.getName());
        serviceDto.setPrice(service.getPrice());
        return serviceDto;
    }
}
