package com.example.smartgarage.models.dtos;

import com.example.smartgarage.models.Service;
import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VisitReportDto {

    private User customer;
    private Vehicle vehicle;
    private List<Service> services;
    private double totalPrice;
    private String currencyCode;
}
