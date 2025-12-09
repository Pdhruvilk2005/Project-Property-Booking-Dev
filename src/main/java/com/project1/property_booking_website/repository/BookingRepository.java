package com.project1.property_booking_website.repository;

//import com.project1.property_booking_website.dto.BookingDTO;
import com.project1.property_booking_website.model.Booking;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByUserEmail(String email);
}
