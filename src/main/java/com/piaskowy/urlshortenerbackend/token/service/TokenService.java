package com.piaskowy.urlshortenerbackend.token.service;

import com.piaskowy.urlshortenerbackend.token.exception.TokenException;
import com.piaskowy.urlshortenerbackend.token.model.Token;
import com.piaskowy.urlshortenerbackend.token.model.TokenType;
import com.piaskowy.urlshortenerbackend.token.repository.TokenRepository;
import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.piaskowy.urlshortenerbackend.token.Utils.generateTokenForUser;

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

    public Token getToken(String token) {
        log.info("Trying obtain token from database with given token string: " + token);
        return tokenRepository
                .findTokenByGeneratedToken(token)
                .orElseThrow();
    }

    public void setConfirmationDate(String token) {
        log.info("Setting confirmation date for token");
        tokenRepository.setTokenConfirmationDate(token, LocalDateTime.now());
    }

    public void validateToken(Token token) {

        if (token.getConfirmedAt() != null) {
            log.error("Token: " + token + " has been already used");
            throw new TokenException("Token with id: " + token.getId() + " has been already used");
        }

        LocalDateTime expiredAt = token.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            log.error("Token: " + token + " expired");
            throw new TokenException("Token with id: " + token.getId() + " expired");
        }
    }

    public Token generateAndSaveToken(User user, TokenType tokenType) {
        Token emailConfirmationToken = generateTokenForUser(user, tokenType);
        return saveGeneratedToken(emailConfirmationToken);
    }
}
