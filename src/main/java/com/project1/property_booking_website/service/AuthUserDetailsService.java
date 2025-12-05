package com.project1.property_booking_website.service;


import com.project1.property_booking_website.model.AuthUserDetail;
import com.project1.property_booking_website.model.User;
import com.project1.property_booking_website.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public AuthUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public AuthUserDetail loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new AuthUserDetail(user);
    }


}
