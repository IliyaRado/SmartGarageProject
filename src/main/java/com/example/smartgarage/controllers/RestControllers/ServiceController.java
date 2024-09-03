package com.example.smartgarage.controllers.RestControllers;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.helpers.ServiceMapper;
import com.example.smartgarage.models.Service;
import com.example.smartgarage.models.dtos.ServiceDto;
import com.example.smartgarage.services.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;
    private final ServiceMapper serviceMapper;

    @Autowired
    public ServiceController(ServiceService serviceService, ServiceMapper serviceMapper) {
        this.serviceService = serviceService;
        this.serviceMapper = serviceMapper;
    }

    @GetMapping
    public List<ServiceDto> getAllServices() {
        List<Service> services = serviceService.getAll();
        return services.stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public Service getServiceByName(@PathVariable String name) {

        try {
            return serviceService.findServiceByName(name);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PostMapping
    public Service createService(@Valid @RequestBody ServiceDto serviceDto) {
        Service service = serviceMapper.fromDto(serviceDto);
        return serviceService.createService(service);

    }

    @PutMapping("/{id}")
    public Service updateService(@PathVariable int id, @Valid @RequestBody ServiceDto serviceDto) {
        Service service = serviceMapper.fromDto(serviceDto);
        return serviceService.update(id, service);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable int id) {
        serviceService.delete(id);
    }
}
