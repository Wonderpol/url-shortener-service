package com.piaskowy.urlshortenerbackend.auth.exception;

public class AuthenticationNotFoundException extends RuntimeException {
    public AuthenticationNotFoundException(final String message) {
        super(message);
    }
}
