package com.microservice.authservice.dto;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthResponse {
    private String token;
    private UserResponse user;
}
