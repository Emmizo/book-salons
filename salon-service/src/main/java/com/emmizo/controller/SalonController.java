package com.emmizo.controller;


import com.emmizo.mapper.SalonMapper;
import com.emmizo.modal.Salon;
import com.emmizo.payload.dto.SalonDTO;
import com.emmizo.payload.dto.UserDTO;
import com.emmizo.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salon")
@RequiredArgsConstructor
public class SalonController {
    private final SalonService salonService;

    @PostMapping("/createSalon")
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.createSalon(salonDTO,userDTO);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return new ResponseEntity<>(salonDTO1,HttpStatus.CREATED);
    }
    @PatchMapping("/updateSalon/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon(@RequestBody SalonDTO salonDTO, @PathVariable Long salonId) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.updateSalon(salonDTO,userDTO,salonId);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return new ResponseEntity<>(salonDTO1,HttpStatus.CREATED);
    }
    @GetMapping("/getSalons")
    public ResponseEntity<List<SalonDTO>> getSalons() throws Exception {

        List<Salon> salons = salonService.getAllSalons();
        List<SalonDTO> salonDTOS = salons.stream().map((salon) -> {
            SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
            return salonDTO;
        }).toList();
return ResponseEntity.ok(salonDTOS);
    }
    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception {

        Salon salons = salonService.getSalonById(salonId);
        SalonDTO salonDTO = SalonMapper.mapToDTO(salons);
        return ResponseEntity.ok(salonDTO);
    }
    @GetMapping("/searchSalons")
    public ResponseEntity<List<SalonDTO>> searchSalons(@RequestParam("city") String city) throws Exception {

        List<Salon> salons = salonService.searchSalonByCity(city);
        List<SalonDTO> salonDTOS = salons.stream().map((salon) -> {
            SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
            return salonDTO;
        }).toList();
        return ResponseEntity.ok(salonDTOS);
    }
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@PathVariable Long ownerId) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salons = salonService.getSalonByOwnerId(userDTO.getId());
        SalonDTO salonDTO = SalonMapper.mapToDTO(salons);
        return ResponseEntity.ok(salonDTO);
    }
}
