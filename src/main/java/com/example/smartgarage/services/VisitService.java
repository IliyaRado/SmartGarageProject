package com.example.smartgarage.services;

import com.example.smartgarage.models.Service;
import com.example.smartgarage.models.Visit;
import com.example.smartgarage.models.dtos.VisitReportDto;
import com.example.smartgarage.models.dtos.VisitUpdateDto;

import java.util.List;

public interface VisitService {

    List<Visit> getAllVisits();
    Visit createVisit(int userId, int vehicleId, List<Integer> serviceIds);
    Visit update(VisitUpdateDto visit);
    void delete(int id);
    Visit findVisitById(int id);
    VisitReportDto generateVisitReport(int visitId, String currencyCode);
}
