package com.vinn.ServiceBookingSystem.controller;

import com.vinn.ServiceBookingSystem.dto.AuthenticationRequest;
import com.vinn.ServiceBookingSystem.dto.SignupRequestDTO;
import com.vinn.ServiceBookingSystem.dto.UserDto;
import com.vinn.ServiceBookingSystem.entity.User;
import com.vinn.ServiceBookingSystem.services.authenntication.AuthService;
import com.vinn.ServiceBookingSystem.services.jwt.UserDetailsServiceImpl;
import com.vinn.ServiceBookingSystem.services.user.UserService;
import com.vinn.ServiceBookingSystem.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    @PostMapping("/client/sign-up")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO) {
        if(authService.presentByEmail(signupRequestDTO.getEmail())) {
            return new ResponseEntity<>("Client already exists with this Email!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto currentUser = authService.signupClient(signupRequestDTO);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @PostMapping("/company/sign-up")
    public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO) {
        if(authService.presentByEmail(signupRequestDTO.getEmail())) {
            return new ResponseEntity<>("Company already exists with this Email!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto currentUser = authService.signupCompany(signupRequestDTO);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @PostMapping({"/authenticate"})
    public void createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response
    ) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken (
                authenticationRequest.getUsername(), authenticationRequest.getPassword()
            ));
            System.out.println("Pass");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        System.out.println(userDetails.getUsername());
        System.out.println("Hello pass");
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        User user = userService.getUserByEmail(authenticationRequest.getUsername());
        System.out.println("Hello pass");
        response.getWriter().write(new JSONObject()
                .put("userId", user.getId())
                .put("role", user.getRole())
                .toString()
        );
        System.out.println("Hello pass");
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Headers", "Authorization," +
                " X-PINGOTHER, Orign, X-Requested-With, Content-Type, Accept, X-Custom-header");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }
}
