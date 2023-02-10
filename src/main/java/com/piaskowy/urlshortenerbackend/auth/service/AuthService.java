package com.piaskowy.urlshortenerbackend.auth.service;

import com.piaskowy.urlshortenerbackend.auth.model.request.AuthenticationRequest;
import com.piaskowy.urlshortenerbackend.auth.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.auth.model.response.AuthenticationResponse;
import com.piaskowy.urlshortenerbackend.config.jwt.JwtService;
import com.piaskowy.urlshortenerbackend.token.model.Token;
import com.piaskowy.urlshortenerbackend.token.model.TokenType;
import com.piaskowy.urlshortenerbackend.token.service.TokenService;
import com.piaskowy.urlshortenerbackend.user.model.CustomUserDetails;
import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class AuthService {
    private final UserRegistrationService userRegistrationService;
    private final AuthEmailService userEmailConfirmationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthService(final UserRegistrationService userRegistrationService,
                       final AuthEmailService userEmailConfirmationService,
                       final AuthenticationManager authenticationManager,
                       final JwtService jwtService, final UserRepository userRepository, final TokenService tokenService) {
        this.userRegistrationService = userRegistrationService;
        this.userEmailConfirmationService = userEmailConfirmationService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        log.info("User registration procedure started with given details: " + registerRequest.toString());
        User user = userRegistrationService.signUpNewUser(registerRequest);
        Token token = tokenService.generateAndSaveToken(user, TokenType.EMAIL_CONFIRM_TOKEN);
        userEmailConfirmationService.sendAccountConfirmationEmail(token.getGeneratedToken(), user);
    }

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
        log.info("User authentication procedure started with given details: " + request.toString());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password())
        );
        String jwtToken = userRepository
                .findByEmail(request.email())
                .map(u -> jwtService.generateToken(new CustomUserDetails(u)))
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + request.email() + " not found"));

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Transactional
    public void confirmUserEmail(String tokenString) {
        log.info("Email confirmation procedure started with token: " + tokenString);
        Token token = tokenService.getToken(tokenString);
        User user = token.getUser();
        tokenService.validateToken(token);
        tokenService.setConfirmationDate(tokenString);
        userRepository.enableUserAccount(user.getEmail());
    }

    public void requestResetPassword(String email) {
        //TODO: reset user password
    }

}
