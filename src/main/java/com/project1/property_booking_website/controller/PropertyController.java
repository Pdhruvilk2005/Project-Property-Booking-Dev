package com.project1.property_booking_website.controller;

import com.project1.property_booking_website.dto.PropertyDTO;
import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.Property;
import com.project1.property_booking_website.service.PropertyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
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
    public ResponseDTO createProperty(@RequestHeader("email") String email, @RequestBody Property property) {

        return propertyService.createProperty(property, email);
    }

    @PatchMapping("{propertyId}")
    public ResponseDTO updateProperty(@RequestHeader("email") String email, @PathVariable String propertyId, @RequestBody PropertyDTO property) {

        return propertyService.updateProperty(email, property, propertyId);
    }

    @DeleteMapping("{propertyId}")
    public ResponseDTO deleteProperty(@RequestHeader("email") String email, @PathVariable String propertyId) {
        return propertyService.deleteProperty(email, propertyId);
    }

    @GetMapping
    public ResponseDTO getProperty(@RequestParam(required = false,defaultValue = "") String search, @RequestParam(required = false, defaultValue = "propertyId") String sortBy, @RequestParam(required = false, defaultValue = "ASC") String order, @RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "5", required = false) int size) {
        log.info(search + " " + sortBy + " " + order + " " + page + " " + size);
        return propertyService.getAllProperties(search, sortBy, order, page, size);
    }

    @PostMapping("is-available/{propertyId}")
    public ResponseDTO isPropertyAvailable(
            @PathVariable String propertyId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {

        return propertyService.isAvailable(propertyId, startDate, endDate);
    }

}
