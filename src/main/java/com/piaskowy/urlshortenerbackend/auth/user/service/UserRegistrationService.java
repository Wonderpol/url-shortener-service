package com.piaskowy.urlshortenerbackend.auth.user.service;

import com.piaskowy.urlshortenerbackend.auth.user.exception.UserAlreadyExistsException;
import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.auth.user.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.auth.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserRegistrationService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUpNewUser(RegisterRequest registerRequest) {

        log.info("Checking if user with email " + registerRequest.getEmail() + " already exists");
        userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("User with email: " + registerRequest.getEmail() + " already exists");
                });

        User user = User.builder()
                .name(registerRequest.getName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("Saving new user " + user + " already exists");
        return userRepository.saveAndFlush(user);
    }
}
