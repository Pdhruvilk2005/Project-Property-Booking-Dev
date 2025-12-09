package com.project1.property_booking_website.dto;

import com.project1.property_booking_website.model.CalendarEntry;
import com.project1.property_booking_website.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {

    private String name;
    private String desc;
    private String address;
    private int maxGuest;
    private int bathrooms;
    private int bedrooms;
    private int minDays;
    private List<String> features;
    private List<Image> images;
    private List<CalendarEntry> calender;
}
