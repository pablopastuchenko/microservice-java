package com.pablo.repository;

import com.pablo.modal.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    Set<ServiceOffering> findBySalonId(Long salonId);
}
