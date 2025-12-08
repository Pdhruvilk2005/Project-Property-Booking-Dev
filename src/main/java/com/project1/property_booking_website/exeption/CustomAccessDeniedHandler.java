package com.project1.property_booking_website.exeption;

//import com.My_Project.User.DTO.ResponseDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.property_booking_website.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    ResponseDTO responseDTO = new ResponseDTO(403, new Date(), null, "you are not eligible");

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDTO));
    }
}
