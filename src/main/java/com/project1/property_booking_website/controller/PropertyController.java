package com.project1.property_booking_website.controller;

import com.project1.property_booking_website.dto.PropertyDTO;
import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.Property;
import com.project1.property_booking_website.service.PropertyService;
import com.project1.property_booking_website.service.PropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/property")
public class PropertyController {


    private final PropertyServiceImpl propertyService;

    @Autowired
    public PropertyController(PropertyServiceImpl propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping()
    public ResponseDTO createProperty(@RequestHeader("email") String email,@RequestBody Property property) {
        return propertyService.createProperty(property,email);
    }

    @PatchMapping("{propertyId}")
    public ResponseDTO updateProperty(@RequestHeader("email") String email,@PathVariable String propertyId,@RequestBody PropertyDTO property) {

        return propertyService.updateProperty(email,property,propertyId);
    }

    @DeleteMapping("{propertyId}")
    public ResponseDTO deleteProperty(@RequestHeader("email") String email,@PathVariable String propertyId) {
        return propertyService.deleteProperty(email, propertyId);
    }

    @GetMapping
    public ResponseDTO getProperty(@RequestHeader("email") String email) {
        return propertyService.getAllProperties(email);
    }
}
