package com.vinn.ServiceBookingSystem.services.authenntication;

import com.vinn.ServiceBookingSystem.dto.SignupRequestDTO;
import com.vinn.ServiceBookingSystem.dto.UserDto;

public interface AuthService {
    UserDto signupClient(SignupRequestDTO signupRequestDTO);

    Boolean presentByEmail(String email);

    UserDto signupCompany(SignupRequestDTO signupRequestDTO);
}
