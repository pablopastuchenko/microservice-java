package com.pablo.controller;

import com.pablo.modal.ServiceOffering;
import com.pablo.payload.dto.CategoryDTO;
import com.pablo.payload.dto.SalonDTO;
import com.pablo.payload.dto.ServiceDTO;
import com.pablo.service.ServiceOfferingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {
    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    private ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO
    ){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategoryId());

        ServiceOffering serviceOfferings = serviceOfferingService.createService(serviceDTO, salonDTO, categoryDTO);

        return ResponseEntity.ok(serviceOfferings);
    }

    @PostMapping("{id}")
    private ResponseEntity<ServiceOffering> updateService(
            @RequestBody Long id,
            @RequestBody ServiceOffering serviceOffering
    ) throws Exception {

        ServiceOffering serviceOfferings = serviceOfferingService.updateService(id, serviceOffering);

        return ResponseEntity.ok(serviceOfferings);
    }
}
