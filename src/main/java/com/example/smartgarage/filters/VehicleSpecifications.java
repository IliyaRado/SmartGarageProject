package com.example.smartgarage.filters;

import com.example.smartgarage.models.User;
import com.example.smartgarage.models.Vehicle;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecifications {
    public static Specification<Vehicle> hasModel(String model) {
        return (root, query, cb) -> cb.equal(root.get("model"), model);
    }

    public static Specification<Vehicle> hasBrand(String brand) {
        return (root, query, cb) -> cb.equal(root.get("brand"), brand);
    }

    public static Specification<Vehicle> hasYearOfCreation(String yearOfCreation) {
        return (root, query, cb) -> cb.equal(root.get("yearOfCreation"), yearOfCreation);
    }
}
