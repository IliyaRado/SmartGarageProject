package com.example.smartgarage.services;

import com.example.smartgarage.models.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceService {

    List<Service> getAll();
    Page<Service> findAll(String nameFilter, Double priceFilter, Pageable pageable);
    Service findServiceByName(String name);
    Service createService(Service service);
    Service update(int id, Service service);
    void delete(int id);
}
