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
public class ActivateAccountEmailService {
    private final EmailService emailService;
    private final EnvironmentVariables environmentVariables;

    public ActivateAccountEmailService(final EmailService emailService, final EnvironmentVariables environmentVariables) {
        this.emailService = emailService;
        this.environmentVariables = environmentVariables;
    }

    @Async
    public void sendAccountConfirmationEmail(String token, User user) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getName());
        properties.put("link", createConfirmEmailLink(environmentVariables.getFrontendUrl(), token));

        final Email email = Email.builder()
                .to(user.getEmail())
                .from("test")
                .subject("Confirm your email")
                .properties(properties)
                .template(environmentVariables.getConfirmEmailTemplateName())
                .build();

        try {
            emailService.sendHtmlEmail(email);
        } catch (MessagingException exception) {
            log.error("Email not sent due to: " + exception.getMessage());
            throw new RuntimeException("Email could not be sent");
        }
    }

    public String createConfirmEmailLink(String frontendUrl, String token) {
        return frontendUrl + environmentVariables.getConfirmEmailFrontendUrl() + "?token=" + token;
    }

}
