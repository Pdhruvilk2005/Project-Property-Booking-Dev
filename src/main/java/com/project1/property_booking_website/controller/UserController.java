package com.project1.property_booking_website.controller;


import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.dto.UserDTO;
import com.project1.property_booking_website.model.User;
import com.project1.property_booking_website.service.UserServiceimpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {


    //    @GetMapping("hello")
//    public String hello() {
//        return "hello";
//    }
    private final UserServiceimpl userServiceimpl;



    @Autowired
    UserController(UserServiceimpl userServiceimpl) {
        this.userServiceimpl = userServiceimpl;

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseDTO createUser(@RequestBody User user) {
        return userServiceimpl.createUser(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userServiceimpl.getAllUsers();
    }


    @GetMapping("{id}")
    public ResponseDTO getUser(@PathVariable("id") int id) {



        return userServiceimpl.getUser(id);
    }


    @PutMapping()
    public ResponseDTO updateUser(@RequestHeader("email") String email, @RequestBody UserDTO user) {
        return userServiceimpl.updateUser(email, user);
    }

    @DeleteMapping()
    public ResponseDTO deleteUser(@RequestHeader("email") String email) {
        return userServiceimpl.deleteUser(email);
    }
}
