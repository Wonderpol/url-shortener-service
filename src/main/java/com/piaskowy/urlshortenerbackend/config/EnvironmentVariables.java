package com.piaskowy.urlshortenerbackend.config;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Primary
@Component
public class EnvironmentVariables {
    @Value("${config.frontend-url}")
    private String frontendUrl;
    @Value("${config.request-password-reset-frontend-endpoint}")
    private String passwordResetFrontendUrl;
    @Value("${config.email-confirm-frontend-endpoint}")
    private String confirmEmailFrontendUrl;
    @Value("${config.jwt.secret-key}")
    private String jwtSecretKey;
    @Value("${config.email-template.confirm-email}")
    private String confirmEmailTemplateName;
    @Value("${config.email-template.reset-password}")
    private String resetPasswordTemplateName;

}
