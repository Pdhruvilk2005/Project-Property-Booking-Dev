package com.project1.property_booking_website.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String bookId;

    private String userId;
    private String propertyId;

    private LocalDate bookingDate;

    private int adult;
    private int child;
    private boolean pet;

    private String paymentSystem;

    private LocalDate from;
    private LocalDate to;

    private String status;

    private boolean cancel;

    @CreatedDate
    private LocalDate createdDt;

    @LastModifiedDate
    private LocalDate updatedDt;

    private int amount;
}

