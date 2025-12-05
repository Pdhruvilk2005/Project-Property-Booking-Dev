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
    public ResponseDTO createProperty(@RequestHeader("userId") String email,@RequestBody Property property) {
        return propertyService.createProperty(property,email);
    }

    @PatchMapping("{propertyId}")
    public ResponseDTO updateProperty(@RequestHeader(name = "Authorization") String token,@PathVariable String propertyId,@RequestBody PropertyDTO property) {

        return propertyService.updateProperty(token,property,propertyId);
    }

    @DeleteMapping("{propertyId}")
    public ResponseDTO deleteProperty(@RequestHeader(name = "Authorization") String token,@PathVariable String propertyId) {
        return propertyService.deleteProperty(token, propertyId);
    }

    @GetMapping
    public ResponseDTO getProperty(@RequestHeader(name = "Authorization") String token) {
        return propertyService.getAllProperties(token);
    }
}
