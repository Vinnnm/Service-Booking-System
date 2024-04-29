package com.vinn.ServiceBookingSystem.entity;

import com.vinn.ServiceBookingSystem.dto.UserDto;
import com.vinn.ServiceBookingSystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String lastName;

    private String phone;

    private UserRole role;
}
