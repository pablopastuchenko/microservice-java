package com.pablo.controller;

import com.pablo.modal.ServiceOffering;
import com.pablo.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    private ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId
    ){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalonId(salonId, categoryId);

        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ServiceOffering> getServiceById(
            @PathVariable Long id

    ) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingService.getServiceById(id);

        return ResponseEntity.ok(serviceOffering);
    }

    @GetMapping("/list/{id}")
    private ResponseEntity<Set<ServiceOffering>> getServicesByIds(
            @PathVariable Set<Long> ids
    ){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServiceByIds(ids);

        return ResponseEntity.ok(serviceOfferings);
    }
}
