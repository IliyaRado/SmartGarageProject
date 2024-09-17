package com.example.smartgarage.services;

import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Vehicle;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.services.UserService;
import com.example.smartgarage.services.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VehicleServiceImplTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Vehicle vehicle;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks

        vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setLicensePlate("CA1234AC");
        vehicle.setVin("1HGBH41JXMN109186");
        vehicle.setYearOfCreation("2010");

        user = new User();
        user.setId(1);
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Vehicle> page = new PageImpl<>(Arrays.asList(vehicle));

        when(vehicleRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<Vehicle> result = vehicleService.findAll(null, null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(vehicleRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testGetAll() {
        when(vehicleRepository.findAll()).thenReturn(Arrays.asList(vehicle));

        List<Vehicle> result = vehicleService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testFindAllVehiclesWithoutUser() {
        when(vehicleRepository.findByUserIsNull()).thenReturn(Arrays.asList(vehicle));

        List<Vehicle> result = vehicleService.findAllVehiclesWithoutUser();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(vehicleRepository, times(1)).findByUserIsNull();
    }

//    @Test
//    void testFindVehicleById() {
//        // Mocking findById() to return Optional.of(vehicle)
//        when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));
//
//        Vehicle result = vehicleService.findVehicleById(1);
//
//        assertNotNull(result);
//        assertEquals(1, result.getId());
//        verify(vehicleRepository, times(1)).findById(1);
//    }

    @Test
    void testFindByLicensePlate() {
        when(vehicleRepository.findByLicensePlate("CA1234AC")).thenReturn(vehicle);

        Vehicle result = vehicleService.findByLicensePlate("CA1234AC");

        assertNotNull(result);
        assertEquals("CA1234AC", result.getLicensePlate());
        verify(vehicleRepository, times(1)).findByLicensePlate("CA1234AC");
    }

    @Test
    void testFindByVin() {
        when(vehicleRepository.findByVin("1HGBH41JXMN109186")).thenReturn(vehicle);

        Vehicle result = vehicleService.findByVin("1HGBH41JXMN109186");

        assertNotNull(result);
        assertEquals("1HGBH41JXMN109186", result.getVin());
        verify(vehicleRepository, times(1)).findByVin("1HGBH41JXMN109186");
    }

    @Test
    void testCreateWithUser() {
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle result = vehicleService.createWithUser(vehicle);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

//    @Test
//    void testUpdate() {
//        // Mocking findById() to return Optional.of(vehicle)
//        when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));
//        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
//
//        Vehicle updatedVehicle = new Vehicle();
//        updatedVehicle.setModel(vehicle.getModel());
//        updatedVehicle.setLicensePlate("CA5678BC");
//        updatedVehicle.setVin("2HGBH41JXMN109186");
//        updatedVehicle.setYearOfCreation("2012");
//
//        Vehicle result = vehicleService.update(updatedVehicle, 1);
//
//        assertNotNull(result);
//        assertEquals("CA5678BC", result.getLicensePlate());
//        assertEquals("2HGBH41JXMN109186", result.getVin());
//        verify(vehicleRepository, times(1)).findById(1);
//        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
//    }
//
//    @Test
//    void testDelete() {
//        // Mocking findById() to return Optional.of(vehicle)
//        when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));
//
//        vehicleService.delete(1);
//
//        verify(vehicleRepository, times(1)).delete(any(Vehicle.class));
//    }
//
//    @Test
//    void testAddVehicleToUser() {
//        // Mocking findById() to return Optional.of(vehicle)
//        when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));
//        when(userService.findUserById(1)).thenReturn(user);
//        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
//
//        Vehicle result = vehicleService.addVehicleToUser(1, 1);
//
//        assertNotNull(result);
//        assertEquals(1, result.getUser().getId());
//        verify(vehicleRepository, times(1)).findById(1);
//        verify(userService, times(1)).findUserById(1);
//        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
//    }
}
