package com.example.smartgarage.filters;

import com.example.smartgarage.models.Service;
import org.springframework.data.jpa.domain.Specification;

public class ServiceSpecifications {


    public static Specification<Service> name(String name) {
        return (root, query, cb) -> cb.equal(root.get("name"), name);
    }

    public static Specification<Service> price(Double price) {
        return (root, query, cb) -> cb.equal(root.get("price"), price);
    }
}
