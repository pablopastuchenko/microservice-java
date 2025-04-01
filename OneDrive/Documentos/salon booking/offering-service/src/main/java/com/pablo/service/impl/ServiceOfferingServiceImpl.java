package com.pablo.service.impl;

import com.pablo.modal.ServiceOffering;
import com.pablo.payload.dto.CategoryDTO;
import com.pablo.payload.dto.SalonDTO;
import com.pablo.payload.dto.ServiceDTO;
import com.pablo.repository.ServiceOfferingRepository;
import com.pablo.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    @Autowired
    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createService(ServiceDTO serviceDTO, SalonDTO salonDTO, CategoryDTO categoryDTO) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setSalonId(salonDTO.getId());
        serviceOffering.setName(serviceDTO.getNome());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setCategoryId(categoryDTO.getId());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(serviceId).orElse(null);

        if (serviceId==null){
            throw new Exception("service not existi with id" + serviceId);

        }

        serviceOffering.setImage(service.getImage());
        serviceOffering.setName(service.getName());
        serviceOffering.setDescription(service.getDescription());
        serviceOffering.setPrice(service.getPrice());
        serviceOffering.setDuration(service.getDuration());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) {
        Set<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);

        if (categoryId!=null){
            services = services.stream().filter((service)->service.getCategoryId()!=null&&
                    service.getCategoryId()==categoryId).collect(Collectors.toSet());
        }
        return services;
    }

    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {
        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElse(null);

        if (serviceOffering==null){
            throw new Exception("service not existi with id" + id);

        }
        return serviceOffering;
    }
}
