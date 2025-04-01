package com.pablo.controller;

import com.pablo.domain.BookingStatus;
import com.pablo.dto.*;
import com.pablo.mapper.BookingMapper;
import com.pablo.modal.Booking;
import com.pablo.modal.SalonReport;
import com.pablo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long salonId,
            @RequestBody BookingRequest bookingRequest
            ) throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);

        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.now());
        salon.setClosetime(LocalTime.now().plusHours(12));

        Set<ServiceDTO> serviceDTOSet = new HashSet<>();

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair cut for men");

        serviceDTOSet.add(serviceDTO);

        Booking booking = bookingService.createBooking(bookingRequest, user, salon, serviceDTOSet);

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(

    ) {
        UserDTO user = new UserDTO();
        user.setId(1L);

        List<Booking> bookings = bookingService.getBookingsByCustomer(1L);


        return ResponseEntity.ok(getBookingDTOs(bookings));

    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(

    ) {
        UserDTO user = new UserDTO();
        user.setId(1L);

        List<Booking> bookings = bookingService.getBookingsBySalon(1L);


        return ResponseEntity.ok(getBookingDTOs(bookings));

    }



    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream()
                .map(booking -> {
                    return BookingMapper.toDTO(booking);
                }).collect(Collectors.toSet());
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingsById(
    @PathVariable Long bookingId,
    @RequestParam BookingStatus status
    ) throws Exception {

        Booking booking = bookingService.updateBooking(bookingId, status);


        return ResponseEntity.ok(BookingMapper.toDTO(booking));

    }

    @GetMapping("/slot/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(
            @PathVariable Long salonId,
            @RequestParam(required = false) LocalDate date
            ) throws Exception {

        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);

        List<BookingSlotDTO> slotsDTOs = bookings.stream()
                .map(booking -> {
                    BookingSlotDTO slotDTO = new BookingSlotDTO();
                    slotDTO.setStartTime(booking.getStartTime());
                    slotDTO.setEndTime(booking.getEndTime());
                    return slotDTO;
                }).collect(Collectors.toList());


        return ResponseEntity.ok(slotsDTOs);

    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(

    ) throws Exception {

        SalonReport report = bookingService.getSalonReport(1L);


        return ResponseEntity.ok(report);

    }
}
