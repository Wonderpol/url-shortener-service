package com.piaskowy.urlshortenerbackend.auth.user.exception;

import com.piaskowy.urlshortenerbackend.globalException.BaseForbiddenException;

public class UserAlreadyExistsException extends BaseForbiddenException {
    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
