package com.project1.property_booking_website.model;

import jakarta.transaction.UserTransaction;
import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private String phone;
    private String address;
    private String password;
}
