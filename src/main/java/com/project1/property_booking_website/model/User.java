// src/main/java/com/project1/property_booking_website/model/User.java
package com.project1.property_booking_website.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank
    @NotEmpty
    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @NotBlank
    @NotEmpty
    @NotNull
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    @NotBlank
    @NotEmpty
    @NotNull
    private String password;


    @NotBlank
    @NotEmpty
    @NotNull
    @Size(min = 10, max = 10)
    private String phone;

    @NotBlank
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime created_dt;

    @UpdateTimestamp
    private LocalDateTime updated_dt;

    private Boolean is_deleted;

}
