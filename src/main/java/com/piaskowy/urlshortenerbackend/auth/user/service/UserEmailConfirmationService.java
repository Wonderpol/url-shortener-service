package com.piaskowy.urlshortenerbackend.auth.user.service;

import com.piaskowy.urlshortenerbackend.auth.token.model.entity.Token;
import com.piaskowy.urlshortenerbackend.auth.token.service.TokenService;
import com.piaskowy.urlshortenerbackend.auth.user.exception.ConfirmationTokenNotFoundException;
import com.piaskowy.urlshortenerbackend.auth.user.exception.EmailIsAlreadyConfirmedException;
import com.piaskowy.urlshortenerbackend.auth.user.exception.TokenExpiredException;
import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.email.EmailService;
import com.piaskowy.urlshortenerbackend.email.model.Email;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.piaskowy.urlshortenerbackend.auth.token.Utils.generateTokenForUser;

@Service
@Log4j2
public class UserEmailConfirmationService {
    private final TokenService tokenService;
    private final EmailService emailService;

    public UserEmailConfirmationService(final TokenService tokenService, final EmailService emailService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
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

    @Async
    public void sendAccountConfirmationEmail(String token, User user) throws MessagingException {

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getName());
        properties.put("link", createConfirmationLink("http://localhost:8080", token));

        final Email email = Email.builder()
                .to(user.getEmail())
                .from("carrentalpo@gmail.com")
                .subject("Confirm your email")
                .properties(properties)
                .template("email-confirm.html")
                .build();

        emailService.sendHtmlEmail(email);
    }

    public String createConfirmationLink(String baseUrl, String token) {
        return baseUrl + "/api/v1/auth/confirm-email?token=" + token;
    }

}
