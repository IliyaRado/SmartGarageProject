package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.Service;
import com.example.smartgarage.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static com.example.smartgarage.filters.ServiceSpecifications.name;
import static com.example.smartgarage.filters.ServiceSpecifications.price;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService{

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> getAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Page<Service> findAll(String nameFilter, Double priceFilter, Pageable pageable) {
        Specification<Service> filters = Specification
                .where(StringUtils.isEmptyOrWhitespace(nameFilter) ? null : name(nameFilter))
                .and(priceFilter == null ? null : price(priceFilter));
        return serviceRepository.findAll(filters, pageable);
    }


    @Override
    public Service findServiceByName(String name) {
        Service service = serviceRepository.findByName(name);
        if (service == null){
            throw new EntityNotFoundException("Service","name", name);
        }
        return service;
    }

    @Override
    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Service update(int id, Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public void delete(int id) {
        serviceRepository.deleteById(id);
    }
}
