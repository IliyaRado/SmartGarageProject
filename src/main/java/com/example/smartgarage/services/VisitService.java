package com.example.smartgarage.services;

import com.example.smartgarage.models.Service;
import com.example.smartgarage.models.Visit;
import com.example.smartgarage.models.dtos.VisitUpdateDto;

import java.util.List;

public interface VisitService {


    Visit createVisit(int userId, int vehicleId, List<Service> services);
    Visit update(VisitUpdateDto visit);
    void delete(int id);
    Visit findVisitById(int id);
    Visit generateVisitReport(int visitId, String currencyCode);
}
