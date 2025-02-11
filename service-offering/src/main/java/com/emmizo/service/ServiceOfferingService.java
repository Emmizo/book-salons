package com.emmizo.service;

import com.emmizo.dto.CategoryDTO;
import com.emmizo.dto.SalonDTO;
import com.emmizo.dto.ServiceDTO;
import com.emmizo.modal.ServiceOffering;

import java.util.List;
import java.util.Set;

public interface ServiceOfferingService {
    ServiceOffering createServiceOffering(SalonDTO SalonDTO,
                                          ServiceDTO serviceDTO,
                                          CategoryDTO categoryDTO);
    ServiceOffering getServiceOfferingById(Long id);
    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);
    ServiceOffering updateService(ServiceOffering serviceOffering,Long serviceId) throws Exception;
    Set<ServiceOffering> getServicesByIds(Set<Long> ids);
    ServiceOffering getServiceById(Long id) throws Exception;

}
