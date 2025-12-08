package com.project1.property_booking_website.repository;

import com.project1.property_booking_website.model.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {

    List<Property> findByAdminEmailAndIsDeleteFalse(String adminEmail);

    @Query("{ 'adminEmail': ?0, 'property_id': ?1, 'isDelete': false }")
    List<Property> findByAdminEmailAndProperty_idAndIsDeleteFalse(String adminEmail, String propertyId);
}
