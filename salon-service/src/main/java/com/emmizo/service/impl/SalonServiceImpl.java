package com.emmizo.service.impl;

import com.emmizo.modal.Salon;
import com.emmizo.payload.dto.SalonDTO;
import com.emmizo.payload.dto.UserDTO;
import com.emmizo.repository.SalonRepository;
import com.emmizo.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

 private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        Salon salon =new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        salon.setPhoneNumber(req.getPhoneNumber());
        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception {
        Salon existingSalon =  salonRepository.findById(salonId).orElse(null);
        if(existingSalon != null && salon.getOwnerId().equals(user.getId())){
            existingSalon.setCity(salon.getCity());
            existingSalon.setName(salon.getName());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setImages(salon.getImages());
            existingSalon.setOwnerId(user.getId());
            existingSalon.setOpenTime(salon.getOpenTime());
            existingSalon.setCloseTime(salon.getCloseTime());
            existingSalon.setPhoneNumber(salon.getPhoneNumber());

        }
       throw new Exception("salon not exist");
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElse(null);
        if (salon==null){
            throw new Exception("salon not exist");
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownId) throws Exception {
        Salon salon = salonRepository.findByOwnerId(ownId);
        if(salon == null){
            throw new Exception("ownerId is not real");
        }
        return salon;
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.SearchSalons(city);
    }
}
