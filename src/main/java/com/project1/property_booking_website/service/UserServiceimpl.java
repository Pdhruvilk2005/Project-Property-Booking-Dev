package com.project1.property_booking_website.service;

import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.jwt.JwtUtil;
import com.project1.property_booking_website.model.User;
import com.project1.property_booking_website.model.UserDTO;
import com.project1.property_booking_website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceimpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceimpl(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByIs_deletedFalse();
    }



    @Override
    public ResponseDTO createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseDTO(200, new Date(), null, "user is already register");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setIs_deleted(false);
        userRepository.save(user);
        return new ResponseDTO(200, new Date(), "user register successfully", null);

    }


    @Override
    public ResponseDTO updateUser(String token, UserDTO user) {
        String validtoken = token.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(validtoken);
        if (userRepository.findByEmail(email).isPresent()) {
            User existingUser = userRepository.findByEmail(email).get();
            existingUser.setName(user.getName());
            existingUser.setPhone(user.getPhone());
            existingUser.setAddress(user.getAddress());
            existingUser.setPassword(user.getPassword());
            userRepository.save(existingUser);
            return new ResponseDTO(200, new Date(), "user updated successfully", null);
        } else {
            return new ResponseDTO(404, new Date(), null, "user not found");
        }
    }

    @Override
    public ResponseDTO deleteUser(String token) {
        String validtoken = token.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(validtoken);
        if (userRepository.findByEmail(email).isPresent()) {
            User user = userRepository.findByEmail(email).get();
            user.setIs_deleted(true);
            userRepository.save(user);
            return new ResponseDTO(200, new Date(), "user deleted successfully", null);
        } else {
            return new ResponseDTO(404, new Date(), null, "user not found");
        }
    }

    @Override
    public User getUserByEmail(String token) {
        String tokenValue=token.substring(7);
//        log.info("tokenValue: "+tokenValue);

        String email = jwtUtil.extractUsername(tokenValue);
//        log.info("email from token: "+email);
        return userRepository.findByEmail(email).orElse(null);
    }
}
