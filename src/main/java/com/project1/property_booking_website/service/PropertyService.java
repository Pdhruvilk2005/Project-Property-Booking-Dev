package com.project1.property_booking_website.service;

import com.project1.property_booking_website.dto.PropertyDTO;
import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.Property;


public interface PropertyService {
    ResponseDTO createProperty(Property property, String token);

    ResponseDTO updateProperty(String token, PropertyDTO property, String propertyId);

    ResponseDTO deleteProperty(String token, String propertyId);


    ResponseDTO getAllProperties(String email, String sortBy, String order, int page, int size);
}
