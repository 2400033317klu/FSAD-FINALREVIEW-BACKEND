package com.career.platform.repository;

import com.career.platform.entity.CareerResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerResourceRepository extends JpaRepository<CareerResource, Long> {
}
