package com.piaskowy.urlshortenerbackend.auth.controller;

import com.piaskowy.urlshortenerbackend.auth.model.request.AuthenticationRequest;
import com.piaskowy.urlshortenerbackend.auth.model.request.NewPasswordRequest;
import com.piaskowy.urlshortenerbackend.auth.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.auth.model.request.ResetPasswordRequest;
import com.piaskowy.urlshortenerbackend.auth.model.response.AuthenticationResponse;
import com.piaskowy.urlshortenerbackend.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
    }

    @PostMapping("authenticate")
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return authService.authenticateUser(authenticationRequest);
    }

    @GetMapping("confirm-email")
    public void confirmEmail(@RequestParam String token) {
        authService.confirmUserEmail(token);
    }

    @GetMapping("request-reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.requestPasswordResetEmail(request.email());
    }

    @GetMapping("reset-password")
    public void resetPassword(@RequestParam String token, @RequestBody NewPasswordRequest request) {
        authService.resetPassword(token, request.newPassword());
    }

}
