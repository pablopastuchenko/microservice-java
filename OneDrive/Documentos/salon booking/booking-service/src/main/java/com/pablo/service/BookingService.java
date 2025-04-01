package com.pablo.service;

import com.pablo.domain.BookingStatus;
import com.pablo.dto.BookingRequest;
import com.pablo.dto.SalonDTO;
import com.pablo.dto.ServiceDTO;
import com.pablo.dto.UserDTO;
import com.pablo.modal.Booking;
import com.pablo.modal.SalonReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BookingService {
    Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBySalon(Long salonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);
}
