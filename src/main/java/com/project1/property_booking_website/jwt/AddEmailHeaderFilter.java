package com.project1.property_booking_website.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AddEmailHeaderFilter extends OncePerRequestFilter {

    JwtUtil jwtUtil;


    @Autowired
    public AddEmailHeaderFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)

            throws ServletException, IOException {

        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);

        // Get email from SecurityContext (after JWT validation)
        String authorizationHeader = request.getHeader("Authorization").substring(7);

        String email = jwtUtil.extractUsername(authorizationHeader);
        log.info("email : {}", email);
        if (email != null)
            mutableRequest.addHeader("email", email);

        filterChain.doFilter(mutableRequest, response);
    }
}

