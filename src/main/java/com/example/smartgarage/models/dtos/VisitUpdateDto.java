package com.example.smartgarage.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VisitUpdateDto {
    private int visitId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer userId;
    private Integer vehicleId;
    private List<Integer> serviceIds;
}
