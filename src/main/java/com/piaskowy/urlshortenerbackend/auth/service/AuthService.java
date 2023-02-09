package com.piaskowy.urlshortenerbackend.auth.service;

import com.piaskowy.urlshortenerbackend.email.EmailService;
import com.piaskowy.urlshortenerbackend.token.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final EmailService emailService;
    private final TokenService tokenService;

    public AuthService(final EmailService emailService, final TokenService tokenService) {
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    public void requestResetPassword(String email) {
        //TODO: reset user password
    }

}
