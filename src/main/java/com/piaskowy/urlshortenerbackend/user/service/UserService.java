package com.piaskowy.urlshortenerbackend.user.service;

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
        checkIfUserExists(request.getEmail());

        User user =
                User.builder()
                        .email(request.getEmail())
                        //TODO: add password encoding
                        .password(request.getPassword())
                        .name(request.getName())
                        .lastName(request.getLastName())
                        .build();

        return userRepository.saveAndFlush(user);
    }
    
    private void checkIfUserExists(String email) {
        userRepository.findByEmail(email)
                //TODO: Add custom exception
                .orElseThrow(() -> new RuntimeException("Istnieje"));
    }
    
}
