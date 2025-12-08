package com.project1.property_booking_website.repository;

import com.project1.property_booking_website.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
