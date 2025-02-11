package com.emmizo.service.impl;

import com.emmizo.dto.CategoryDTO;
import com.emmizo.dto.SalonDTO;
import com.emmizo.dto.ServiceDTO;
import com.emmizo.modal.ServiceOffering;
import com.emmizo.repository.ServiceOfferingRepository;
import com.emmizo.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {
private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createServiceOffering(SalonDTO SalonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setSalonId(SalonDTO.getId());
        serviceOffering.setCategoryId(categoryDTO.getId());
        serviceOffering.setPrice(serviceDTO.getPrice());
        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering getServiceOfferingById(Long id) {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElse(null);
        return serviceOffering;
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) {
        Set<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);
        if (categoryId != null) {
            services= services.stream().filter(s->s.getCategoryId().equals(categoryId)).collect(Collectors.toSet());
        }
        return services;
    }

    @Override
    public ServiceOffering updateService(ServiceOffering serviceOffering, Long serviceId) throws Exception {
        ServiceOffering serviceOfferings = serviceOfferingRepository.findById(serviceId).orElse(null);
if (serviceOfferings == null) {
    throw new Exception("service not exist with id "+serviceId);
}
        serviceOfferings.setImage(serviceOffering.getImage());
        serviceOfferings.setName(serviceOffering.getName());
        serviceOfferings.setDescription(serviceOffering.getDescription());
        serviceOfferings.setPrice(serviceOffering.getPrice());
        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public Set<ServiceOffering> getServicesByIds(Set<Long> ids) {

        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElse(null);
        if (serviceOffering == null) {
            throw new Exception("Service not exist with id "+id);
        }
        return serviceOffering;
    }
}
