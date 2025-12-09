package com.project1.property_booking_website.service;

//import com.project1.property_booking_website.dto.BookingDTO;
import com.project1.property_booking_website.dto.ResponseDTO;
import com.project1.property_booking_website.dto.UserBookingDTO;
import com.project1.property_booking_website.dto.UserDTO;
import com.project1.property_booking_website.jwt.JwtUtil;
import com.project1.property_booking_website.model.Booking;
import com.project1.property_booking_website.model.User;
import com.project1.property_booking_website.repository.BookingRepository;
import com.project1.property_booking_website.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserServiceimpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceimpl(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
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
            if (userRepository.findByEmail(user.getEmail()).get().getIsDeleted()) {
                return new ResponseDTO(406, new Date(), null, "user is deleted");
            }
            return new ResponseDTO(200, new Date(), null, "user is already register");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setIsDeleted(false);
        userRepository.save(user);
        return new ResponseDTO(200, new Date(), "user register successfully", null);

    }


    @Override
    public ResponseDTO updateUser(String email, UserDTO user) {

        if (userRepository.findByEmail(email).isPresent()) {
            if (userRepository.findByEmail(email).get().getIsDeleted()) {
                return new ResponseDTO(406, new Date(), null, "user is deleted");
            }
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
    public ResponseDTO deleteUser(String email) {

        if (userRepository.findByEmail(email).isPresent()) {
            if (userRepository.findByEmail(email).get().getIsDeleted()) {
                return new ResponseDTO(406, new Date(), null, "user is deleted");
            }
            User user = userRepository.findByEmail(email).get();
            user.setIsDeleted(true);
            userRepository.save(user);
            return new ResponseDTO(200, new Date(), "user deleted successfully", null);
        } else {
            return new ResponseDTO(404, new Date(), null, "user not found");
        }
    }

    @Override
    public ResponseDTO getUser(int id) {

        if(userRepository.findById(id).isEmpty()){
            return new ResponseDTO(404, new Date(), null, "user not found");
        }
        else if (userRepository.findById(id).get().getIsDeleted()) {
            return new ResponseDTO(406, new Date(), null, "user is deleted");
        }
        else{
            User user=userRepository.findById(id).get();
            List<Booking> bookingDTO=bookingRepository.findByUserEmail(user.getEmail());
            log.info(user.toString()+"-----------------");
            return new ResponseDTO(200, new Date(), new UserBookingDTO(user,bookingDTO), null);
        }
    }
}
