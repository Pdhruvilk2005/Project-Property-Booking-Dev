package com.project1.property_booking_website.repository;

import com.project1.property_booking_website.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    List<User> findAllByIs_deletedFalse();

    Optional<User> findByEmail(String email);
}
