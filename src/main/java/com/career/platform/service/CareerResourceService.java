package com.career.platform.service;

import com.career.platform.entity.CareerResource;
import com.career.platform.repository.CareerResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerResourceService {

    @Autowired
    private CareerResourceRepository repository;

    public List<CareerResource> getAllResources() {
        return repository.findAll();
    }

    public CareerResource addResource(CareerResource resource) {
        return repository.save(resource);
    }
}
