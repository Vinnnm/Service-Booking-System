package com.vinn.ServiceBookingSystem.services.user;

import com.vinn.ServiceBookingSystem.entity.User;
import com.vinn.ServiceBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }
}
