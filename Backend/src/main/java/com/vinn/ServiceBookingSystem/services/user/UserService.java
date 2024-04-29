package com.vinn.ServiceBookingSystem.services.user;

import com.vinn.ServiceBookingSystem.entity.User;

public interface UserService {
    User getUserByEmail(String email);
}
