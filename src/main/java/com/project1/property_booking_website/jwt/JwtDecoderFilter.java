package com.project1.property_booking_website.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtDecoderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        CustomHeaderRequestWrapper requestWrapper = new CustomHeaderRequestWrapper(request);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            DecodedJWT jwt = JWT.decode(token);  // NO SECRET REQUIRED

            String userId = jwt.getClaim("userId").asString();

            if (userId != null) {
                requestWrapper.addHeader("userId", userId);  // <-- Add HEADER
            }
        }

        filterChain.doFilter(requestWrapper, response);
    }
}
