package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Service;
import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Vehicle;
import com.example.smartgarage.models.Visit;
import com.example.smartgarage.models.dtos.VisitUpdateDto;
import com.example.smartgarage.repositories.ServiceRepository;
import com.example.smartgarage.repositories.UserRepository;
import com.example.smartgarage.repositories.VehicleRepository;
import com.example.smartgarage.repositories.VisitRepository;

import java.time.LocalDate;
import java.util.List;

@org.springframework.stereotype.Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceRepository serviceRepository;
//    private final CurrencyService currencyService;

    public VisitServiceImpl(VisitRepository visitRepository, UserRepository userRepository, VehicleRepository vehicleRepository, ServiceRepository serviceRepository) {
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Visit createVisit(int userId, int vehicleId, List<Service> services) {
        User user = userRepository.findById(userId);
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Visit visit = new Visit();
        visit.setUser(user);
        visit.setVehicle(vehicle);
        visit.setStartDate(LocalDate.now());
        visit.setServices(services);
        double totalPrice = services.stream().mapToDouble(Service::getPrice).sum();
        visit.setTotalPrice(totalPrice);

        return visitRepository.save(visit);
    }

    @Override
    public Visit update(VisitUpdateDto visitUpdateDto) {

        Visit visit = visitRepository.findById(visitUpdateDto.getVisitId())
                .orElseThrow(() -> new EntityNotFoundException("Visit", visitUpdateDto.getVisitId()));

        visit.setStartDate(visitUpdateDto.getStartDate());
        visit.setEndDate(visitUpdateDto.getEndDate());

        User user = userRepository.findById(visitUpdateDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User", visitUpdateDto.getUserId()));
        visit.setUser(user);

        Vehicle vehicle = vehicleRepository.findById(visitUpdateDto.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle", visitUpdateDto.getVehicleId()));
        visit.setVehicle(vehicle);

        List<Service> services = serviceRepository.findAllById(visitUpdateDto.getServiceIds());
        visit.setServices(services);

        return visitRepository.save(visit);

    }

    @Override
    public void delete(int visitId) {

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException("Visit", visitId));
        visitRepository.delete(visit);
    }

    @Override
    public Visit findVisitById(int visitId) {
        return visitRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException("Visit", visitId));
    }

    @Override
    public Visit generateVisitReport(int visitId, String currencyCode) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new EntityNotFoundException("Visit", visitId));
//        if (!"USD".equalsIgnoreCase(currencyCode)) {
//            double convertedTotalPrice = currencyConversionService.convert(visit.getTotalPrice(), "USD", currencyCode);
//            visit.setTotalPrice(convertedTotalPrice);
//        }
        return visit;
    }
}
