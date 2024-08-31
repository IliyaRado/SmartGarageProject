package com.example.smartgarage.controllers.RestControllers;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Service;
import com.example.smartgarage.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<Service> getAllServices() {
        return serviceService.getAll();
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
    public Service createService(Service service) {

        return serviceService.createService(service);
    }

    @PutMapping("/{id}")
    public Service updateService(@PathVariable int id, @RequestBody Service service) {

        return serviceService.update(id, service);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable int id) {
        serviceService.delete(id);
    }
}
