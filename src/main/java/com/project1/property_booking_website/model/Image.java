package com.project1.property_booking_website.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {

    private int id;

    private String propertyId;

    private String url;

    private int order;
}
