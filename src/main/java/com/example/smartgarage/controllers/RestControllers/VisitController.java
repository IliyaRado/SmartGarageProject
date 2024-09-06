package com.example.smartgarage.controllers.RestControllers;

import com.example.smartgarage.models.Service;
import com.example.smartgarage.models.Visit;
import com.example.smartgarage.models.dtos.VisitReportDto;
import com.example.smartgarage.models.dtos.VisitUpdateDto;
import com.example.smartgarage.services.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<Visit> createVisit(@RequestParam int userId,
                                             @RequestParam int vehicleId,
                                             @RequestBody List<Service> services) {
        Visit visit = visitService.createVisit(userId, vehicleId, services);
        return ResponseEntity.ok(visit);
    }

    @PutMapping("/{visitId}")
    public ResponseEntity<Visit> updateVisit(@PathVariable int visitId,
                                             @RequestBody VisitUpdateDto visitUpdateDto) {
        visitUpdateDto.setVisitId(visitId);
        Visit updatedVisit = visitService.update(visitUpdateDto);
        return ResponseEntity.ok(updatedVisit);
    }

    @DeleteMapping("/{visitId}")
    public ResponseEntity<Void> deleteVisit(@PathVariable int visitId) {
        visitService.delete(visitId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{visitId}/report")
    public ResponseEntity<VisitReportDto> generateVisitReport(@PathVariable int visitId,
                                                              @RequestParam(defaultValue = "USD") String currency) {
        VisitReportDto report = visitService.generateVisitReport(visitId, currency);
        return ResponseEntity.ok(report);
    }
}
