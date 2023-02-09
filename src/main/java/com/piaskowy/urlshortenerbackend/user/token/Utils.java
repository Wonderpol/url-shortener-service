package com.piaskowy.urlshortenerbackend.user.token;

import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.user.token.model.Token;
import com.piaskowy.urlshortenerbackend.user.token.model.TokenType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Utils {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static Token generateTokenForUser(User user, TokenType tokenType) {
        return Token.builder()
                .generatedToken(Utils.generateToken())
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .tokenType(tokenType)
                .build();
    }

}
