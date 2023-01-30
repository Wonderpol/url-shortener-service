package com.piaskowy.urlshortenerbackend.user.service;

import com.piaskowy.urlshortenerbackend.config.EnvironmentVariables;
import com.piaskowy.urlshortenerbackend.email.EmailService;
import com.piaskowy.urlshortenerbackend.email.model.Email;
import com.piaskowy.urlshortenerbackend.user.exception.ConfirmationTokenNotFoundException;
import com.piaskowy.urlshortenerbackend.user.exception.EmailIsAlreadyConfirmedException;
import com.piaskowy.urlshortenerbackend.user.exception.TokenExpiredException;
import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.user.token.model.Token;
import com.piaskowy.urlshortenerbackend.user.token.service.TokenService;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.piaskowy.urlshortenerbackend.user.token.Utils.generateTokenForUser;

@Service
@Log4j2
public class UserEmailConfirmationService {
    private final TokenService tokenService;
    private final EmailService emailService;
    private final EnvironmentVariables environmentVariables;

    public UserEmailConfirmationService(final TokenService tokenService, final EmailService emailService, final EnvironmentVariables environmentVariables) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.environmentVariables = environmentVariables;
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
    public void sendAccountConfirmationEmail(String token, User user) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getName());
        properties.put("link", createConfirmationLink(environmentVariables.getFrontendUrl(), token));

        final Email email = Email.builder()
                .to(user.getEmail())
                .from("test")
                .subject("Confirm your email")
                .properties(properties)
                .template("email-confirm.html")
                .build();

        try {
            emailService.sendHtmlEmail(email);
        } catch (MessagingException exception) {
            log.error("Email not sent due to: " + exception.getMessage());
            throw new RuntimeException("Email could not be sent");
        }
    }

    public String createConfirmationLink(String frontendUrl, String token) {
        return frontendUrl + "?token=" + token;
    }

}
