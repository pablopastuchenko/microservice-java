package com.pablo.service.impl;

import com.pablo.domain.BookingStatus;
import com.pablo.dto.BookingRequest;
import com.pablo.dto.SalonDTO;
import com.pablo.dto.ServiceDTO;
import com.pablo.dto.UserDTO;
import com.pablo.modal.Booking;
import com.pablo.modal.SalonReport;
import com.pablo.repository.BookingRepository;
import com.pablo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    @Autowired
    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet) throws Exception {

        int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();

        LocalDateTime bookingStartTime=booking.getStartTime();
        LocalDateTime bookingEndTime=bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvaible = isTimeSlotAvaible(salon, bookingStartTime, bookingEndTime);

        int totalPrice = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getPrice)
                .sum();

        Set<Long> idList = serviceDTOSet.stream()
                .map(ServiceDTO::getId)
                .collect(Collectors.toSet());

        Booking newBooking = new Booking();

        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvaible(SalonDTO salonDTO,
                                     LocalDateTime bookingStartTime,
                                     LocalDateTime bookingEndTime) throws Exception {

        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());

        LocalDateTime salonOpenTime= salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());

        LocalDateTime salonCloseTime = salonDTO.getClosetime().atDate(bookingStartTime.toLocalDate());

        if (bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)){
            throw new Exception("Booking time must be within salon's working hours");
        }

        for (Booking existingBooking: existingBookings) {
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existitngBeforeEndTime = existingBooking.getEndTime();

            if (bookingStartTime.isBefore(existitngBeforeEndTime) && bookingEndTime.isAfter(existingBookingStartTime)) {
                throw new Exception("slot not avaible, choose different time.");
            }
            if (bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual((existitngBeforeEndTime))) {
                throw new Exception("slot not avaible, choose different time.");
            }

        }
        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking==null){
            throw new Exception("booking not found");
        }
        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);

        booking.setStatus(status);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = getBookingsBySalon(salonId);

        if (date==null) {
            return allBookings;
        }

        allBookings.stream()
                .filter(booking -> isSameDate(booking.getStartTime(), date) ||
                        isSameDate(booking.getEndTime(),date))
                .collect(Collectors.toSet());

        return List.of();
    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);

        int totalEarnings = bookings.stream()
                .mapToInt(Booking::getTotalPrice)
                .sum();

        Integer totalBooking = bookings.size();

        List<Booking> cancelledBookings = bookings.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED))
                .collect(Collectors.toList());

        Double totalRefund = cancelledBookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        SalonReport report = new SalonReport();
        report.setSalonId(salonId);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalBookings(totalEarnings);
        report.setTotalEarnings(totalEarnings);
        report.setTotalRefund(totalRefund);
        report.setTotalBookings(totalBooking);

        return report;
    }
}
