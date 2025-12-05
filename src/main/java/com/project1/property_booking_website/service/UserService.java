package com.project1.property_booking_website.service;

import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.User;
import com.project1.property_booking_website.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    List<User> getAllUsers();



    ResponseDTO createUser(User user);

    ResponseDTO updateUser(String email, UserDTO user);

    ResponseDTO deleteUser(String id);

    Object getUser(String email);
}
