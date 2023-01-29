package com.piaskowy.urlshortenerbackend.auth.controller;

import com.piaskowy.urlshortenerbackend.auth.model.request.AuthenticationRequest;
import com.piaskowy.urlshortenerbackend.auth.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.auth.model.response.AuthenticationResponse;
import com.piaskowy.urlshortenerbackend.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(userService.authenticateUser(authenticationRequest), HttpStatus.OK);
    }

    @GetMapping("confirm-email")
    public void confirm(@RequestParam String token) {
        userService.confirmUserEmail(token);
    }

}
