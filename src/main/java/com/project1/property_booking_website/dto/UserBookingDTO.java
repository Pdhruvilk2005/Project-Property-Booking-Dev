package com.project1.property_booking_website.dto;

import com.project1.property_booking_website.model.Booking;
import com.project1.property_booking_website.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBookingDTO{
    private User user;
    private List<Booking> bookings;
}
