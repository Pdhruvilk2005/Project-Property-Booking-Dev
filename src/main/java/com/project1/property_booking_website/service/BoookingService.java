package com.project1.property_booking_website.service;

import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.Booking;

public interface BoookingService {
    ResponseDTO createBooking(Booking booking, String email);

    ResponseDTO cancleBooking(String email, String bookingId);
}
