package com.vinn.ServiceBookingSystem.services.authenntication;

import com.vinn.ServiceBookingSystem.dto.SignupRequestDTO;
import com.vinn.ServiceBookingSystem.dto.UserDto;
import com.vinn.ServiceBookingSystem.entity.User;
import com.vinn.ServiceBookingSystem.enums.UserRole;
import com.vinn.ServiceBookingSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements  AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setLastName(signupRequestDTO.getLastName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.CLIENT);
        userRepository.save(user);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Boolean presentByEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    @Override
    public UserDto signupCompany(SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.COMPANY);
        userRepository.save(user);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }
}
