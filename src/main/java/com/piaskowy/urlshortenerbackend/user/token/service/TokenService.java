package com.piaskowy.urlshortenerbackend.user.token.service;

import com.piaskowy.urlshortenerbackend.user.token.model.Token;
import com.piaskowy.urlshortenerbackend.user.token.repository.TokenRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Log4j2
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token saveGeneratedToken(Token token) {
        return tokenRepository.saveAndFlush(token);
    }

    public Optional<Token> getToken(String token) {
        log.info("Trying obtain token from database with given token string: " + token);
        return tokenRepository.findTokenByGeneratedToken(token);
    }

    public void setConfirmationDate(String token) {
        log.info("Setting confirmation date for token");
        tokenRepository.setTokenConfirmationDate(token, LocalDateTime.now());
    }

}
