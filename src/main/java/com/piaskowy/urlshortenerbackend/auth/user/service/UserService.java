package com.piaskowy.urlshortenerbackend.auth.user.service;

import com.piaskowy.urlshortenerbackend.auth.token.model.entity.Token;
import com.piaskowy.urlshortenerbackend.auth.user.model.CustomUserDetails;
import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.auth.user.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.auth.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRegistrationService userRegistrationService;
    private final UserEmailConfirmationService userEmailConfirmationService;

    UserService(final UserRepository userRepository, final UserRegistrationService userRegistrationService, final UserEmailConfirmationService userEmailConfirmationService) {
        this.userRepository = userRepository;
        this.userRegistrationService = userRegistrationService;
        this.userEmailConfirmationService = userEmailConfirmationService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        return userRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
    }

    public User registerUser(RegisterRequest registerRequest) {
        User user = userRegistrationService.signUpNewUser(registerRequest);
        Token token = userEmailConfirmationService.generateAndSaveConfirmationToken(user);

        return user;
    }

    public void confirmUserEmail(String token) {
        User user = userEmailConfirmationService.validateEmailConfirmationToken(token).getUser();
        userRepository.enableUserAccount(user.getEmail());
    }

}
