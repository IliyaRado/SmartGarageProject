package com.example.smartgarage.services;

import com.example.smartgarage.models.Service;
import com.example.smartgarage.repositories.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceServiceImplTests {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateService() {
        Service service = new Service();
        service.setName("Oil Change");
        service.setPrice(29.99);

        when(serviceRepository.save(any(Service.class))).thenReturn(service);

        Service createdService = serviceService.createService(service);

        assertNotNull(createdService);
        assertEquals("Oil Change", createdService.getName());
        assertEquals(29.99, createdService.getPrice());
    }

    @Test
    void testGetServiceById() {
        Service service = new Service();
        service.setId(1);
        service.setName("Tire Rotation");

        when(serviceRepository.findById(1)).thenReturn(Optional.of(service));

        Service foundService = serviceService.findServiceById(1);

        assertNotNull(foundService);
        assertEquals(1, foundService.getId());
        assertEquals("Tire Rotation", foundService.getName());
    }

    @Test
    void testUpdateService() {
        Service service = new Service();
        service.setId(1);
        service.setName("Brake Inspection");
        service.setPrice(49.99);

        when(serviceRepository.findById(1)).thenReturn(Optional.of(service));
        when(serviceRepository.save(any(Service.class))).thenReturn(service);

        service.setName("Brake Inspection Updated");
        service.setPrice(59.99);

        Service updatedService = serviceService.update(1, service);

        assertNotNull(updatedService);
        assertEquals("Brake Inspection Updated", updatedService.getName());
        assertEquals(59.99, updatedService.getPrice());
    }

    @Test
    void testDeleteService() {
        Service service = new Service();
        service.setId(1);

        when(serviceRepository.findById(1)).thenReturn(Optional.of(service));
        doNothing().when(serviceRepository).deleteById(1);

        serviceService.delete(1);

        verify(serviceRepository, times(1)).deleteById(1);
    }
}