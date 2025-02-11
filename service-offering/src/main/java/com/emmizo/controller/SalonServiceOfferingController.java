package com.emmizo.controller;

import com.emmizo.dto.CategoryDTO;
import com.emmizo.dto.SalonDTO;
import com.emmizo.dto.ServiceDTO;
import com.emmizo.modal.ServiceOffering;
import com.emmizo.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {
    private final ServiceOfferingService serviceOfferingService;

    @PostMapping("/create-offer")
    public ResponseEntity<ServiceOffering> createService(@RequestBody ServiceDTO serviceDTO) {

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategoryId());

        ServiceOffering serviceOfferings =serviceOfferingService.createServiceOffering(salonDTO, serviceDTO, categoryDTO);
        return ResponseEntity.ok(serviceOfferings);

    }
    @PatchMapping("/update-offer/{serviceId}")
    public ResponseEntity<ServiceOffering> updateService(@PathVariable Long serviceId,
                                                         @RequestBody ServiceOffering serviceOffering) throws Exception {

        ServiceOffering serviceOfferings =serviceOfferingService.updateService(serviceOffering, serviceId);
        return ResponseEntity.ok(serviceOfferings);

    }
}
