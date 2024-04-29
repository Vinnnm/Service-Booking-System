package com.vinn.ServiceBookingSystem.dto;

import com.vinn.ServiceBookingSystem.enums.UserRole;
import lombok.Data;

@Data
public class SignupRequestDTO {
    private Long id;

    private String email;

    private String password;

    private String name;

    private String lastName;

    private String phone;
}
