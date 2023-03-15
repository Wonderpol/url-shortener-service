package com.piaskowy.urlshortenerbackend.auth.service;

import com.piaskowy.urlshortenerbackend.config.EnvironmentVariables;
import com.piaskowy.urlshortenerbackend.email.EmailService;
import com.piaskowy.urlshortenerbackend.email.model.Email;
import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

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

    @Test
    void test_sendPasswordResetEmail() throws MessagingException {
        //given
        String token = "12345678";
        User user = User.builder()
                .id(1L)
                .email("example@example.com")
                .password("test")
                .name("Test")
                .lastName("Test")
                .build();

        Map<String, Object> expectedProperties = new HashMap<>();
        expectedProperties.put("name", user.getName());
        expectedProperties.put("link", passwordResetService.createResetPasswordLink(token));

        given(environmentVariables.getResetPasswordTemplateName()).willReturn("reset-password.html");

        //when
        passwordResetService.sendPasswordResetEmail(token, user);
        //then
        ArgumentCaptor<Email> emailArgumentCaptor = ArgumentCaptor.forClass(Email.class);
        verify(emailService).sendHtmlEmail(emailArgumentCaptor.capture());
        Email sentEmail = emailArgumentCaptor.getValue();
        assertEquals(user.getEmail(), sentEmail.to());
        assertEquals("noreply@test.com", sentEmail.from());
        assertEquals(environmentVariables.getResetPasswordTemplateName(), sentEmail.template());
        assertEquals(expectedProperties, sentEmail.properties());
    }

    @Test
    void test_sendPasswordResetEmail_willThrow() throws MessagingException {
        //given
        String token = "12345678";
        User user = User.builder()
                .id(1L)
                .email("example@example.com")
                .password("test")
                .name("Test")
                .lastName("Test")
                .build();
        given(environmentVariables.getResetPasswordTemplateName()).willReturn("reset-password.html");
        doThrow(MessagingException.class).when(emailService).sendHtmlEmail(any());

        //when & then
        assertThatThrownBy(() -> passwordResetService.sendPasswordResetEmail(token, user))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email could not be sent");
    }

}