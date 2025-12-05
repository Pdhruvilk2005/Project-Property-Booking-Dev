package com.project1.property_booking_website.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEntry {



    private String dateStr;
    private boolean arrival;
    private boolean departure;
    private boolean available;
    private String basePrice;
    @LastModifiedDate
    private Date updatedDt;
    private int minStay;
    private Date date;
}
