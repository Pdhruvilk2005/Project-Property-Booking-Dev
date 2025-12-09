package com.project1.property_booking_website.service;

import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.Booking;
import com.project1.property_booking_website.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class BookingServiceImpl implements BoookingService {

    private final PropertyServiceImpl propertyService;
    private final BookingRepository bookingRepository;
    public BookingServiceImpl(PropertyServiceImpl propertyService, BookingRepository bookingRepository) {
        this.propertyService = propertyService;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public ResponseDTO createBooking(Booking booking, String email) {

        ResponseDTO requestDTO = propertyService.isAvailable(booking.getPropertyId(), booking.getFrom(), booking.getTo());
        if (requestDTO.getStatus() != 200) {
            return new ResponseDTO(requestDTO.getStatus(), new Date(), requestDTO.getErrorResponse(), null);
        } else {
            booking.setUserEmail(email);
            if ((booking.getAdult() + booking.getChild()) > propertyService.getPropety(booking.getPropertyId()).getMaxGuest()) {
                log.info(booking.getAdult() + booking.getChild() + " " + propertyService.getPropety(booking.getPropertyId()).getMaxGuest());
                return new ResponseDTO(401, new Date(), "number of guests exceeded", null);
            }
            log.info(booking.toString());
            booking.setCancel(false);
            double price = propertyService.ChangingFlagsOnDates(booking.getPropertyId(), booking.getFrom(), booking.getTo(), false);
            booking.setAmount(price);
            bookingRepository.save(booking);
            return new ResponseDTO(requestDTO.getStatus(), new Date(), "successfully booked property", null);
        }
    }

    @Override
    public ResponseDTO cancleBooking(String email, String bookingId) {

        if (bookingRepository.findById(bookingId).isPresent()) {
            Booking booking = bookingRepository.findById(bookingId).get();
            if (booking.getUserEmail().equals(email)) {
                propertyService.ChangingFlagsOnDates(booking.getPropertyId(), booking.getFrom(), booking.getTo(), true);
                booking.setCancel(true);
                bookingRepository.save(booking);
                return new ResponseDTO(200, new Date(), "Booking cancelled successfully", null);
            } else {
                return new ResponseDTO(403, new Date(), "You are not authorized to cancel this booking", null);
            }
        } else {
            return new ResponseDTO(404, new Date(), "Booking not found", null);
        }
    }
}
