package com.piaskowy.urlshortenerbackend.auth.service;

import com.piaskowy.urlshortenerbackend.config.EnvironmentVariables;
import com.piaskowy.urlshortenerbackend.email.EmailService;
import com.piaskowy.urlshortenerbackend.email.model.Email;
import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class PasswordResetService {

    private final EnvironmentVariables environmentVariables;
    private final EmailService emailService;

    public PasswordResetService(final EnvironmentVariables environmentVariables, final EmailService emailService) {
        this.environmentVariables = environmentVariables;
        this.emailService = emailService;
    }

    @Async
    public void sendPasswordResetEmail(String token, User user) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getName());
        properties.put("link", createConfirmEmailLink(environmentVariables.getFrontendUrl(), token));

        final Email email = Email.builder()
                .to(user.getEmail())
                .from("noreply@test.com")
                .subject("Reset your password")
                .properties(properties)
                .template(environmentVariables.getResetPasswordTemplateName())
                .build();

        try {
            emailService.sendHtmlEmail(email);
        } catch (MessagingException exception) {
            log.error("Email not sent due to: " + exception.getMessage());
            throw new RuntimeException("Email could not be sent");
        }
    }

    public String createConfirmEmailLink(String frontendUrl, String token) {
        //TODO: extract "/reset-password" to env variable
        return frontendUrl + "/reset-password?token=" + token;
    }
}
