package com.emmizo.service;

import com.emmizo.modal.Salon;
import com.emmizo.payload.dto.SalonDTO;
import com.emmizo.payload.dto.UserDTO;

import java.util.List;

public interface SalonService {
    Salon createSalon(SalonDTO salon, UserDTO user);

    Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception;

    List<Salon> getAllSalons();

    Salon getSalonById(Long id) throws Exception;

    Salon getSalonByOwnerId(Long ownId) throws Exception;

    List<Salon> searchSalonByCity(String city);

}
