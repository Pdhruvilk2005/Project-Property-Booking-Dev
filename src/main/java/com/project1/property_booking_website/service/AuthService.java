package com.project1.property_booking_website.service;

import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.LoginRequest;
import com.project1.property_booking_website.model.User;

public interface AuthService {
    ResponseDTO login(LoginRequest request);

    ResponseDTO register(User user);
}
