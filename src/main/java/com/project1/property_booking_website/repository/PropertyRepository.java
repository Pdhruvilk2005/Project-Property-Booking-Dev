package com.project1.property_booking_website.repository;

import com.project1.property_booking_website.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {

    @Query("{'isDelete': false,'$or': [{ 'name': { $regex: ?0, $options: 'i' }},{ 'desc': { $regex: ?0, $options: 'i' }},{ 'address': { $regex: ?0, $options: 'i' }}]}")
    Page<Property> findByIsDeleteFalseAndSearch(String keyword, Pageable pageable);


    @Query("{ 'adminEmail': ?0, 'property_id': ?1, 'isDelete': false }")
    List<Property> findByAdminEmailAndProperty_idAndIsDeleteFalse(String adminEmail, String propertyId);
}
