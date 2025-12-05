package com.project1.property_booking_website.service;

import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.jwt.JwtUtil;
import com.project1.property_booking_website.model.LoginRequest;
import com.project1.property_booking_website.model.User;
import com.project1.property_booking_website.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AuthServiceImpl implements  AuthService {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(AuthenticationManager authManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseDTO login(LoginRequest request) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).get();
        return new ResponseDTO(200, new Date(), jwtUtil.generateToken(user.getEmail(), user.getRole().name()), null);
    }

    @Override
    public ResponseDTO register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseDTO(406, new Date(), null, "user is already register");
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setIs_deleted(false);
            userRepository.save(user);

            return new ResponseDTO(200, new Date(), "user register successfully", null);
        }

    }
}
