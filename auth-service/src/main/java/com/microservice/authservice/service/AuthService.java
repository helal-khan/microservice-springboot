package com.microservice.authservice.service;

import com.microservice.authservice.dto.AuthRequest;
import com.microservice.authservice.dto.AuthResponse;
import com.microservice.authservice.dto.UserRequest;

public interface AuthService {

    String saveUser(UserRequest user);

    AuthResponse generateToken(AuthRequest authRequest);

    void validateToken(String token);
}
