package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.*;
import com.example.smartgarage.models.dtos.AppointmentDto;
import com.example.smartgarage.models.dtos.VisitReportDto;
import com.example.smartgarage.models.dtos.VisitUpdateDto;
import com.example.smartgarage.repositories.*;

import java.time.LocalDate;
import java.util.List;

@org.springframework.stereotype.Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceRepository serviceRepository;
    private final CurrencyConversionService currencyConversionService;
    private final ModelRepository modelRepository;

    public VisitServiceImpl(VisitRepository visitRepository, UserRepository userRepository, VehicleRepository vehicleRepository, ServiceRepository serviceRepository, CurrencyConversionService currencyConversionService, ModelRepository modelRepository) {
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRepository = serviceRepository;
        this.currencyConversionService = currencyConversionService;
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public Visit createVisit(int userId, int vehicleId, List<Integer> serviceIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        List<Service> services = serviceRepository.findAllById(serviceIds);
        if (services.isEmpty()) {
            throw new RuntimeException("No valid services found for the provided IDs.");
        }

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
        if (services.isEmpty()) {
            throw new RuntimeException("No valid services found for the provided IDs.");
        }
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
    public VisitReportDto generateVisitReport(int visitId, String currencyCode) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException("Visit", visitId));

        List<Service> services = visit.getServices();
        if (services.isEmpty()) {
            throw new RuntimeException("No services found for this visit.");
        }

        double totalPrice = visit.getServices().stream()
                .mapToDouble(Service::getPrice)
                .sum();

        if (!currencyCode.equalsIgnoreCase("EUR")) {
            totalPrice = currencyConversionService.convertCurrency(totalPrice, currencyCode);
        }

        VisitReportDto report = new VisitReportDto();
        report.setCustomer(visit.getUser());
        report.setVehicle(visit.getVehicle());
        report.setServices(visit.getServices());
        report.setTotalPrice(totalPrice);
        report.setCurrencyCode(currencyCode);

        return report;
    }

    @Override
    public Visit createVisit(AppointmentDto appointmentDto) {
        User user = userRepository.findByUsername(appointmentDto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User", "Username", appointmentDto.getUsername()));
        Model model = modelRepository.findById(appointmentDto.getModelId())
                .orElseThrow(() -> new EntityNotFoundException("Model", appointmentDto.getModelId()));
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(appointmentDto.getLicensePlate());
        vehicle.setVin(appointmentDto.getVin());
        vehicle.setYearOfCreation(appointmentDto.getYearOfCreation());
        vehicle.setModel(model);
        vehicle.setUser(user);
        try {
            vehicle = vehicleRepository.save(vehicle);
            var visit= createVisit(user.getId(), vehicle.getId(), appointmentDto.getServiceIds());
            generateVisitReport(visit.getId(), "EUR");
            return visit;
        } catch (Exception e) {
            System.out.println("VisitServiceImpl.createVisit: -143 line should be refactored" + e.getMessage());
            System.out.println(e.getMessage());
        }
//todo: implement the start and end date and generateVisitReport() fix

        return null;

    }
}
