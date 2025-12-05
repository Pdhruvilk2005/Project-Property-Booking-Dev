package com.project1.property_booking_website.service;

import com.mongodb.DuplicateKeyException;
import com.project1.property_booking_website.dto.PropertyDTO;
import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.jwt.JwtUtil;
import com.project1.property_booking_website.model.Property;
import com.project1.property_booking_website.repository.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.propertyRepository = propertyRepository;
    }
    @Override
    public ResponseDTO createProperty(Property property, String email) {

        try {
            property.setAdminEmail(email);
            Property savedProperty = propertyRepository.insert(property);
        } catch (DuplicateKeyException e) {
            throw e;
        }
        log.info(property.toString());


        return new ResponseDTO(201, new Date(), "Property created successfully", null);
    }

    @Override
    public ResponseDTO updateProperty(String token, PropertyDTO property, String propertyId) {

        String email = jwtUtil.extractUsername(token.substring(7));

        log.info("email: {}", email);

        if (propertyRepository.findById(propertyId).isEmpty()) {
            return new ResponseDTO(404, new Date(), null, "Property not found");
        }

        if (propertyRepository.findByAdminEmailAndProperty_idAndIsDeleteFalse(email, propertyId).isEmpty()) {
            return new ResponseDTO(403, new Date(), null, "You are not authorized to update this property");
        }


        Property existingProperty = propertyRepository.findById(propertyId).get();

        if (!property.getName().isEmpty()){
            existingProperty.setName(property.getName());
        }
        if (!property.getAddress().isEmpty()) {
            existingProperty.setAddress(property.getAddress());
        }
        if (!property.getDesc().isEmpty()) {
        existingProperty.setDesc(property.getDesc());
        }
        if (property.getBathrooms()>0) {
        existingProperty.setBathrooms(property.getBathrooms());
        }
        if (property.getMaxGuest()>0){
        existingProperty.setMaxGuest(property.getMaxGuest());
        }
        if(property.getBedrooms()>0) {
            existingProperty.setBedrooms(property.getBedrooms());
        }
        if (property.getFeatures()!=null) {
        existingProperty.setFeatures(property.getFeatures());
        }
        if (property.getCalender()!=null) {
            existingProperty.setCalender(property.getCalender());
        }
        if (property.getImages()!=null) {
        existingProperty.setImages(property.getImages());
        }
        propertyRepository.save(existingProperty);
        return new ResponseDTO(200, new Date(), "Property updated successfully", null);
    }

    @Override
    public ResponseDTO deleteProperty(String token, String propertyId) {

        String email = jwtUtil.extractUsername(token.substring(7));

        log.info("email: {}", email);

        if (propertyRepository.findById(propertyId).isEmpty()) {
            return new ResponseDTO(404, new Date(), null, "Property not found");
        }

        if (propertyRepository.findByAdminEmailAndProperty_idAndIsDeleteFalse(email, propertyId).isEmpty()) {
            return new ResponseDTO(403, new Date(), null, "You are not authorized to delete this property");
        }

        Property existingProperty = propertyRepository.findById(propertyId).get();
        existingProperty.setDelete(true);
        propertyRepository.save(existingProperty);
        return new ResponseDTO(200, new Date(), "Property deleted successfully", null);
    }

    @Override
    public ResponseDTO getAllProperties(String token) {
        String email = jwtUtil.extractUsername(token.substring(7));

        log.info("email: {}", email);
        if (propertyRepository.findByAdminEmailAndIsDeleteFalse(email).isEmpty()) {
            return new ResponseDTO(404, new Date(), null, "Property not found");
        }
        return new ResponseDTO(200, new Date(), propertyRepository.findByAdminEmailAndIsDeleteFalse(email), null);
    }
}
