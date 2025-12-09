package com.project1.property_booking_website.controller;

import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.Booking;
import com.project1.property_booking_website.service.BookingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingServiceImpl bookingService;

    @Autowired
    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseDTO createBooking(@RequestBody Booking booking, @RequestHeader("email") String email) {

        return bookingService.createBooking(booking, email);

    }

    @DeleteMapping
    public ResponseDTO deleteBooking(@RequestHeader("email") String email,@RequestParam String BookingId) {
        return bookingService.cancleBooking(email,BookingId);
    }
}
