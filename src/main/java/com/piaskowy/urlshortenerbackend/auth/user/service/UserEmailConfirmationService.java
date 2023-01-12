package com.piaskowy.urlshortenerbackend.auth.user.service;

import com.piaskowy.urlshortenerbackend.auth.token.model.entity.Token;
import com.piaskowy.urlshortenerbackend.auth.token.service.TokenService;
import com.piaskowy.urlshortenerbackend.auth.user.exception.ConfirmationTokenNotFoundException;
import com.piaskowy.urlshortenerbackend.auth.user.exception.EmailIsAlreadyConfirmedException;
import com.piaskowy.urlshortenerbackend.auth.user.exception.TokenExpiredException;
import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.piaskowy.urlshortenerbackend.auth.token.Utils.generateTokenForUser;

@Service
@Log4j2
public class UserEmailConfirmationService {
    private final TokenService tokenService;

    public UserEmailConfirmationService(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Token generateAndSaveConfirmationToken(User user) {
        Token emailConfirmationToken = generateTokenForUser(user);
        return tokenService.saveGeneratedToken(emailConfirmationToken);
    }

    public Token validateEmailConfirmationToken(String token) {

        Token confirmationToken = tokenService
                .getToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailIsAlreadyConfirmedException("Email is already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token " + token + " expired");
        }

        tokenService.setConfirmationDate(token);

        return confirmationToken;
    }

}
