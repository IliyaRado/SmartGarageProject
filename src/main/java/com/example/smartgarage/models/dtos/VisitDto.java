package com.example.smartgarage.models.dtos;

import com.example.smartgarage.models.Service;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VisitDto {

    private int userId;

    private int vehicleId;

    @NotEmpty(message = "At least one service must be provided.")
    private List<Integer> serviceIds;
}
