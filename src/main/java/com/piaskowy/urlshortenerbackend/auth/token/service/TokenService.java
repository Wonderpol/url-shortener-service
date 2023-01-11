package com.piaskowy.urlshortenerbackend.auth.token.service;

import com.piaskowy.urlshortenerbackend.auth.token.model.entity.Token;
import com.piaskowy.urlshortenerbackend.auth.token.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token saveGeneratedToken(Token token) {
        return tokenRepository.saveAndFlush(token);
    }

    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void setConfirmationDate(String token) {
        tokenRepository.setTokenConfirmationDate(token, LocalDateTime.now());
    }

}
