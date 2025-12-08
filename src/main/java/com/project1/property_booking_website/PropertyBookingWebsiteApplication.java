package com.project1.property_booking_website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PropertyBookingWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyBookingWebsiteApplication.class, args);
    }

}
