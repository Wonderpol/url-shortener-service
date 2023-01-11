package com.piaskowy.urlshortenerbackend.auth.token;

import com.piaskowy.urlshortenerbackend.auth.token.model.entity.Token;
import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class Utils {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static Token generateTokenForUser(User user) {
        return Token.builder()
                .token(Utils.generateToken())
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
    }

}
