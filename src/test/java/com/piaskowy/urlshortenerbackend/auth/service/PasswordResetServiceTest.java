package com.piaskowy.urlshortenerbackend.auth.service;

import com.piaskowy.urlshortenerbackend.config.EnvironmentVariables;
import com.piaskowy.urlshortenerbackend.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceTest {

    @Mock
    EnvironmentVariables environmentVariables;
    @Mock
    EmailService emailService;

    @InjectMocks
    PasswordResetService passwordResetService;

    @Test
    void test_createResetPasswordLink() {
        //given
        String frontendUrl = "https://example.com";
        String token = "12345678";
        String resetPasswordFrontendEndpoint = "/reset-password";
        String expectedLink = frontendUrl + resetPasswordFrontendEndpoint + "?token=" + token;
        given(environmentVariables.getFrontendUrl()).willReturn(frontendUrl);
        given(environmentVariables.getPasswordResetFrontendUrl()).willReturn(resetPasswordFrontendEndpoint);

        //when
        String generatedLink = passwordResetService.createResetPasswordLink(token);

        //then
        assertEquals(expectedLink, generatedLink);
    }

}