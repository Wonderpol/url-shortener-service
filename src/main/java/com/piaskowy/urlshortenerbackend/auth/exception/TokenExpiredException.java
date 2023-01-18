package com.piaskowy.urlshortenerbackend.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(final String message) {
        super(message);
    }
}
