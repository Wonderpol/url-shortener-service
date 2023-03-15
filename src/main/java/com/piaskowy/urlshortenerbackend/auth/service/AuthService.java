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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class AuthService {
    private final UserRegistrationService userRegistrationService;
    private final ActivateAccountEmailService userEmailConfirmationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordResetService passwordResetService;
    private final PasswordEncoder passwordEncoder;


    public AuthService(final UserRegistrationService userRegistrationService,
                       final ActivateAccountEmailService userEmailConfirmationService,
                       final AuthenticationManager authenticationManager,
                       final JwtService jwtService, final UserRepository userRepository, final TokenService tokenService, final PasswordResetService passwordResetService, final PasswordEncoder passwordEncoder) {
        this.userRegistrationService = userRegistrationService;
        this.userEmailConfirmationService = userEmailConfirmationService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordResetService = passwordResetService;
        this.passwordEncoder = passwordEncoder;
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

    public void requestPasswordResetEmail(String email) {
        log.info("User with email: " + email + " requested password reset");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));

        Token token = tokenService.generateAndSaveToken(user, TokenType.EMAIL_CONFIRM_TOKEN);
        passwordResetService.sendPasswordResetEmail(token.getGeneratedToken(), user);
    }

    @Transactional
    public void resetPassword(String tokenString, String newPassword) {
        log.info("Reset password procedure started with token: " + tokenString);
        Token token = tokenService.getToken(tokenString);
        User user = token.getUser();
        tokenService.validateToken(token);
        tokenService.setConfirmationDate(tokenString);
        userRepository.updateUserPassword(passwordEncoder.encode(newPassword), user.getId());
    }
}
