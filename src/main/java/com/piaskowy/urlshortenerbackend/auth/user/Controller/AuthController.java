package com.piaskowy.urlshortenerbackend.auth.user.Controller;

import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.auth.user.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.auth.user.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserRegistrationService userRegistrationService;

    public AuthController(final UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(userRegistrationService.registerUser(registerRequest), HttpStatus.OK);
    }
}
