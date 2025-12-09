package com.project1.property_booking_website.model;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "property")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property implements Persistable<String> {

    @Id
    private String propertyId;
    private String adminEmail;
    private String name;//add
    private String desc;//add
    private String address;//add
    private boolean isDelete;
//    private int minDays;

    private List<String> features;//add

    @CreatedDate
    private Date createdDt;

    @LastModifiedDate
    private Date updatedDt;

    @Positive
    private int maxGuest;
    @Positive
    private int bathrooms;
    @Positive
    private int bedrooms;

    private List<Image> images; //add
    private List<CalendarEntry> calender;//add


    @Override
    public @Nullable String getId() {
        return propertyId;
    }

    @Override
    public boolean isNew() {
        return createdDt == null;
    }


}
