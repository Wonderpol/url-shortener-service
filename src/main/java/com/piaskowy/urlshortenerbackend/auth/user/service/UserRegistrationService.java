package com.piaskowy.urlshortenerbackend.auth.user.service;

import com.piaskowy.urlshortenerbackend.auth.user.exception.UserAlreadyExistsException;
import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.auth.user.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.auth.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserRegistrationService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUpNewUser(RegisterRequest registerRequest) {

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

        return userRepository.saveAndFlush(user);
    }
}
