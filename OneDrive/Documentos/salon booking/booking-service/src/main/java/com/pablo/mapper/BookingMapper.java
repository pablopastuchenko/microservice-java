package com.pablo.mapper;

import com.pablo.dto.BookingDTO;
import com.pablo.modal.Booking;

public class BookingMapper {
    public static BookingDTO toDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setEndTime(booking.getStartTime());
        bookingDTO.setSalonId(booking.getSalonId());
        bookingDTO.setServiceIds(booking.getServiceIds());

        return bookingDTO;
    }

}
