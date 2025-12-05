package com.project1.property_booking_website.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}