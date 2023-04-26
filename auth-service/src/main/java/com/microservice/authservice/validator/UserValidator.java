package com.microservice.authservice.validator;


import com.microservice.authservice.dto.AuthRequest;
import com.microservice.authservice.dto.UserRequest;
import com.microservice.authservice.repository.UserRepository;
import com.microservice.authservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;
    Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN);

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(AuthRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequest userRequest = (UserRequest) target;

        if (StringUtils.isBlank(userRequest.getName())) {
            errors.rejectValue("name", "name.is.required", Constants.NAME_REQUIRED);
        }
        if (StringUtils.isBlank(userRequest.getUsername())) {
            errors.rejectValue("username", "username.is.required", Constants.USERNAME_REQUIRED);
        }
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            errors.rejectValue("username", "username.is.taken", Constants.USERNAME_TAKEN);
        }
        if (StringUtils.isBlank(userRequest.getPassword())) {
            errors.rejectValue("password", "password.is.required", Constants.PASSWORD_REQUIRED);
        }
        if (StringUtils.isBlank(userRequest.getEmail())) {
            errors.rejectValue("email", "email.is.required", Constants.EMAIL_REQUIRED);
        }
        if (!pattern.matcher(userRequest.getEmail()).matches()) {
            errors.rejectValue("email", "email.is.invalid", Constants.INVALID_EMAIL);
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            errors.rejectValue("email", "email.is.exist", Constants.EMAIL_EXISTS);
        }
    }
}
