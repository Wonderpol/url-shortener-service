package com.piaskowy.urlshortenerbackend.auth.user.exception;

import com.piaskowy.urlshortenerbackend.globalException.BaseForbiddenException;

public class EmailIsAlreadyConfirmedException extends BaseForbiddenException {
    public EmailIsAlreadyConfirmedException(final String message) {
        super(message);
    }
}
