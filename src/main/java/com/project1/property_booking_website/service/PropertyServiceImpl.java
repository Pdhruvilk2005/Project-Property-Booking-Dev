package com.project1.property_booking_website.service;

import com.project1.property_booking_website.dto.PropertyDTO;
import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.CalendarEntry;
import com.project1.property_booking_website.model.Property;
import com.project1.property_booking_website.repository.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;


    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository) {

        this.propertyRepository = propertyRepository;
    }

    public static long daysBetween(Date d1, Date d2) {
        long diffMillis = d2.getTime() - d1.getTime();
        return diffMillis / (1000 * 60 * 60 * 24);
    }

    private static boolean isSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public ResponseDTO createProperty(Property property, String email) {

        property.setAdminEmail(email);
        Property savedProperty = propertyRepository.insert(property);

        log.info(property.toString());


        return new ResponseDTO(201, new Date(), "Property created successfully", null);
    }

    @Override
    public ResponseDTO updateProperty(String email, PropertyDTO property, String propertyId) {


        if (propertyRepository.findById(propertyId).isEmpty()) {
            return new ResponseDTO(404, new Date(), null, "Property not found");
        }

        if (propertyRepository.findByAdminEmailAndProperty_idAndIsDeleteFalse(email, propertyId).isEmpty()) {
            return new ResponseDTO(403, new Date(), null, "You are not authorized to update this property");
        }


        Property existingProperty = propertyRepository.findById(propertyId).get();

        if (!property.getName().isEmpty()) {
            existingProperty.setName(property.getName());
        }
        if (!property.getAddress().isEmpty()) {
            existingProperty.setAddress(property.getAddress());
        }
        if (!property.getDesc().isEmpty()) {
            existingProperty.setDesc(property.getDesc());
        }
        if (property.getBathrooms() > 0) {
            existingProperty.setBathrooms(property.getBathrooms());
        }
        if (property.getMaxGuest() > 0) {
            existingProperty.setMaxGuest(property.getMaxGuest());
        }
        if (property.getBedrooms() > 0) {
            existingProperty.setBedrooms(property.getBedrooms());
        }
        if (property.getFeatures() != null) {
            existingProperty.setFeatures(property.getFeatures());
        }
        if (property.getCalender() != null) {
            existingProperty.setCalender(property.getCalender());
        }
        if (property.getImages() != null) {
            existingProperty.setImages(property.getImages());
        }
        propertyRepository.save(existingProperty);
        return new ResponseDTO(200, new Date(), "Property updated successfully", null);
    }

    @Override
    public ResponseDTO deleteProperty(String email, String propertyId) {


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
    public ResponseDTO getAllProperties(String search, String sortBy, String order, int page, int size) {
        Sort sort;
        if (Objects.equals(order, "DESC")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        if (propertyRepository.findByIsDeleteFalseAndSearch(search, pageable).getContent().isEmpty()) {
            return new ResponseDTO(404, new Date(), null, "Property not found");
        }
        return new ResponseDTO(200, new Date(), propertyRepository.findByIsDeleteFalseAndSearch(search, pageable).getContent(), null);
    }

    public ResponseDTO is_available(String propertyId, Date from, Date to) {

        if (propertyRepository.findById(propertyId).isEmpty()) {
            return new ResponseDTO(404, new Date(), null, "Property not found");
        }

        Property property = propertyRepository.findById(propertyId).get();
        List<CalendarEntry> calendar = property.getCalender();

        ZoneId zone = ZoneId.systemDefault();

        LocalDate fromDate = from.toInstant().atZone(zone).toLocalDate();
        LocalDate toDate = to.toInstant().atZone(zone).toLocalDate();

        List<CalendarEntry> range = calendar.stream().sorted(Comparator.comparing(entry -> entry.getDate().toInstant().atZone(zone).toLocalDate())).filter(entry -> {
            if (entry.getDate() == null) return false;

            LocalDate entryDate = entry.getDate().toInstant().atZone(zone).toLocalDate();

            return !entryDate.isBefore(fromDate) && !entryDate.isAfter(toDate);
        }).toList();


        int days = Period.between(fromDate, toDate).getDays();
        log.info(range.toString());

        if ((days + 1) != range.size()) {
            return new ResponseDTO(201, new Date(), "Property is not available", null);
        }


        if (range.isEmpty()) {
            return new ResponseDTO(200, new Date(), "Property is not available", null);
        }

        // Check minimum stay
        long stayDays = daysBetween(from, to);
        if (range.get(0).getMinStay() > stayDays) {
            return new ResponseDTO(200, new Date(), "you have to stay for minimum days", null);
        }

        boolean flag = true;


        for (CalendarEntry entry : range) {

            LocalDate entryDate = entry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            log.info("equals from: {}", entryDate.equals(fromDate));
            log.info("equals to: {}", entryDate.equals(toDate));

            if (entryDate.equals(fromDate)) {
                if (!entry.isArrival() || !entry.isAvailable()) flag = false;
            } else if (entryDate.equals(toDate)) {
                if (!entry.isDeparture()) flag = false;
            } else {
                if (!entry.isAvailable()) flag = false;
            }


        }

        if (flag) return new ResponseDTO(200, new Date(), "Property is available", null);
        else return new ResponseDTO(200, new Date(), "Property is not available", null);
    }

}
