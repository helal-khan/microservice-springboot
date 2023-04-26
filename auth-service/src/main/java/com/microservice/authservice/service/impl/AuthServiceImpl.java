package com.microservice.authservice.service.impl;

import com.microservice.authservice.dto.AuthRequest;
import com.microservice.authservice.dto.AuthResponse;
import com.microservice.authservice.dto.UserRequest;
import com.microservice.authservice.dto.UserResponse;
import com.microservice.authservice.entity.User;
import com.microservice.authservice.repository.UserRepository;
import com.microservice.authservice.security.JwtService;
import com.microservice.authservice.security.UserPrincipal;
import com.microservice.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String saveUser(final UserRequest user) {
        userRepository.save(User.builder()
                .name(user.getName())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .build()
        );
        return "User added successfully";
    }

    @Override
    public AuthResponse generateToken(final AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrincipal userDetails = (UserPrincipal) authenticate.getPrincipal();

        return AuthResponse.builder()
                .token(jwtService.generateToken(userDetails.getUsername()))
                .user(
                        UserResponse.builder()
                                .name(userDetails.getName())
                                .username(userDetails.getUsername())
                                .email(userDetails.getEmail())
                                .build()
                )
                .build();
    }

    @Override
    public void validateToken(final String token) {
        jwtService.validateToken(token);
    }
}
