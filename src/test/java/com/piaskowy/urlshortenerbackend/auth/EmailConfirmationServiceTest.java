package com.piaskowy.urlshortenerbackend.auth;

import com.piaskowy.urlshortenerbackend.auth.service.AuthEmailService;
import com.piaskowy.urlshortenerbackend.token.model.Token;
import com.piaskowy.urlshortenerbackend.token.service.TokenService;
import com.piaskowy.urlshortenerbackend.user.exception.ConfirmationTokenNotFoundException;
import com.piaskowy.urlshortenerbackend.user.exception.EmailIsAlreadyConfirmedException;
import com.piaskowy.urlshortenerbackend.user.exception.TokenExpiredException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailConfirmationServiceTest {

    @Mock
    private TokenService tokenService;
    @InjectMocks
    private AuthEmailService userEmailConfirmationService;

    @Test
    void test_validateEmailConfirmationToken() {
        //given
        Token token = Token.builder()
                .generatedToken("Token")
                .createdAt(LocalDateTime.now().minusDays(5))
                .expiresAt(LocalDateTime.now().plusDays(10))
                .build();

        given(tokenService.getToken(anyString())).willReturn(Optional.of(token));
        //when
        userEmailConfirmationService.validateEmailConfirmationToken(token.getGeneratedToken());
        //then

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(tokenService).setConfirmationDate(argumentCaptor.capture());

        String capturedValue = argumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(token.getGeneratedToken());
    }

    @Test
    void test_validateEmailConfirmationToken_shouldThrow_ConfirmationTokenNotFoundException() {
        //given
        given(tokenService.getToken(anyString())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> userEmailConfirmationService.validateEmailConfirmationToken(anyString()))
                .isInstanceOf(ConfirmationTokenNotFoundException.class)
                .hasMessageContaining("Token not found");
    }

    @Test
    void test_validateEmailConfirmationToken_shouldThrow_EmailIsAlreadyConfirmedException() {
        //given
        Token token = Token.builder()
                .usedAt(LocalDateTime.now())
                .generatedToken("Token")
                .expiresAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now().minusDays(5))
                .build();

        given(tokenService.getToken(anyString())).willReturn(Optional.of(token));
        //when
        //then
        assertThatThrownBy(() -> userEmailConfirmationService.validateEmailConfirmationToken(anyString()))
                .isInstanceOf(EmailIsAlreadyConfirmedException.class)
                .hasMessageContaining("Email is already confirmed");
    }

    @Test
    void test_validateEmailConfirmationToken_shouldThrow_TokenExpiredException() {
        //given
        Token token = Token.builder()
                .generatedToken("Token")
                .createdAt(LocalDateTime.now().minusDays(5))
                .expiresAt(LocalDateTime.now().minusDays(1))
                .build();

        given(tokenService.getToken(anyString())).willReturn(Optional.of(token));
        //when
        //then
        assertThatThrownBy(() -> userEmailConfirmationService.validateEmailConfirmationToken(token.getGeneratedToken()))
                .isInstanceOf(TokenExpiredException.class)
                .hasMessageContaining("Token " + token.getGeneratedToken() + " expired");
    }

}