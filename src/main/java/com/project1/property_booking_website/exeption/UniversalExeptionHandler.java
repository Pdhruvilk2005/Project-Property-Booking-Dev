package com.project1.property_booking_website.exeption;

import com.mongodb.DuplicateKeyException;
import com.project1.property_booking_website.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class UniversalExeptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(UniversalExeptionHandler.class);

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseDTO handleDuplicateKeyException(DuplicateKeyException ex) {
        logger.error("Duplicate Key Exception: ", ex);
        return new ResponseDTO(400, new Date(),null, "Duplicate key error: " + ex.getMessage());
    }

}
