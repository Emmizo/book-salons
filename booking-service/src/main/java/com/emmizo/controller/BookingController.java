package com.emmizo.controller;

import com.emmizo.domain.BookingStatus;
import com.emmizo.dto.*;
import com.emmizo.mapper.BookingMapper;
import com.emmizo.modal.Booking;
import com.emmizo.modal.SalonReport;
import com.emmizo.service.BookingService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

     @PostMapping("create-booking")
    public ResponseEntity<Booking> addBooking(@RequestParam Long salonId, @RequestBody BookingRequest bookingRequest) throws Exception {
        SalonDTO salon =new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.now());
        salon.setCloseTime(LocalTime.now().plusHours(12));

        UserDTO user = new UserDTO();
        user.setId(1L);
        Set<ServiceDTO> serviceDTOSet = new HashSet<>();
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair cut for men");

        serviceDTOSet.add(serviceDTO);
        Booking newBooking = bookingService.createBooking(bookingRequest,
                user,
                salon,
                serviceDTOSet);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }
    public ResponseEntity<BookingDTO> getBooking(@PathParam("bookingId") Long bookingId) throws Exception {
         return null;
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(){

         List<Booking> bookings = bookingService.getBookingsBySalon(1L);

         return ResponseEntity.ok(getBookingsDTOs(bookings));
    }
    private Set<BookingDTO> getBookingsDTOs(List<Booking> bookings) {
         return bookings.stream().map(BookingMapper::toBookingDTO).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingsById(@PathVariable Long bookingId) throws Exception {

       Booking bookings = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toBookingDTO(bookings));
    }
    @GetMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingsStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
    ) throws Exception {

        Booking bookings = bookingService.updateBooking(bookingId, status);

        return ResponseEntity.ok(BookingMapper.toBookingDTO(bookings));
    }
    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(@PathVariable Long salonId, @RequestParam(required = false) LocalDate date) throws Exception {

        List<Booking> bookings = bookingService.getBookingsByDate(date,salonId);
    List<BookingSlotDTO> slotsDTOs =  bookings.stream().map(booking -> {
                BookingSlotDTO slotDTO = new BookingSlotDTO();
                slotDTO.setStartTime(booking.getStartTime());
                slotDTO.setEndTime(booking.getEndTime());
                return slotDTO;
            }).collect(Collectors.toList());
        return ResponseEntity.ok(slotsDTOs);
    }
    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport() throws Exception {

        SalonReport report = bookingService.getSalonReport(1L);

        return ResponseEntity.ok(report);
    }
}
