package com.emmizo.service;

import com.emmizo.domain.BookingStatus;
import com.emmizo.dto.BookingRequest;
import com.emmizo.dto.SalonDTO;
import com.emmizo.dto.ServiceDTO;
import com.emmizo.dto.UserDTO;
import com.emmizo.modal.Booking;
import com.emmizo.modal.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {
    Booking createBooking(BookingRequest booking,
                          UserDTO user,
                          SalonDTO salonDTO,
                          Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingsByCustomerId(Long customerId);
    List<Booking> getBookingsBySalon(Long salonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long id, BookingStatus status) throws Exception;
    void deleteBooking(Long id);
    List<Booking> getBookingsByDate(LocalDate date,Long salonId);

    SalonReport getSalonReport(Long salonId);


}
