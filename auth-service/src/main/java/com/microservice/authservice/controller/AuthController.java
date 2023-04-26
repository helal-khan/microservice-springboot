package com.microservice.authservice.controller;

import com.microservice.authservice.dto.AuthRequest;
import com.microservice.authservice.dto.AuthResponse;
import com.microservice.authservice.dto.UserRequest;
import com.microservice.authservice.entity.User;
import com.microservice.authservice.service.AuthService;
import com.microservice.authservice.util.Utility;
import com.microservice.authservice.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserValidator userValidator;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserRequest user, BindingResult result) throws ValidationException {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            throw new ValidationException(Utility.getErrors(result));
        }
        return authService.saveUser(user);
    }

    @PostMapping("/token")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.generateToken(authRequest);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}
