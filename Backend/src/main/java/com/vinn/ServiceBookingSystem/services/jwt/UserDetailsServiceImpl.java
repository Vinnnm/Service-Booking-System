package com.vinn.ServiceBookingSystem.services.jwt;

import com.vinn.ServiceBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.vinn.ServiceBookingSystem.entity.User user = userRepository.findFirstByEmail(email);
        if(user == null) throw new UsernameNotFoundException("Username not found", null);
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
