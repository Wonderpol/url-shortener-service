package com.piaskowy.urlshortenerbackend.user.service;

import com.piaskowy.urlshortenerbackend.user.exception.UserAlreadyExistsException;
import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.user.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("User with email: " + request.getEmail() + " already exists");
                });

        User user = User.builder()
                        .email(request.getEmail())
                        //TODO: add password encoding
                        .password(request.getPassword())
                        .name(request.getName())
                        .lastName(request.getLastName())
                        .build();

        return userRepository.saveAndFlush(user);
    }
}
