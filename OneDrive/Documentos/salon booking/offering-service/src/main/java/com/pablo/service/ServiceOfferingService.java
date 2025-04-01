package com.pablo.service;

import com.pablo.payload.dto.CategoryDTO;
import com.pablo.payload.dto.SalonDTO;
import com.pablo.modal.ServiceOffering;
import com.pablo.payload.dto.ServiceDTO;

import java.util.List;
import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(ServiceDTO serviceDTO,
                                  SalonDTO salonDTO,
                                  CategoryDTO categoryDTO);
    ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);

    Set<ServiceOffering> getServiceByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;
}
