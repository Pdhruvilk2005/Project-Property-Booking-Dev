package com.project1.property_booking_website.controller;


import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.model.LoginRequest;
import com.project1.property_booking_website.model.User;
import com.project1.property_booking_website.service.AuthServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    public AuthController(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("/register")
    public ResponseDTO register(@Validated @RequestBody User user) {
        return authServiceImpl.register(user);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginRequest request) {
        return authServiceImpl.login(request);
    }


}
