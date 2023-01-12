package com.piaskowy.urlshortenerbackend.auth.user.exception;

import com.piaskowy.urlshortenerbackend.globalException.BaseForbiddenException;

public class TokenExpiredException extends BaseForbiddenException {
    public TokenExpiredException(final String message) {
        super(message);
    }
}
