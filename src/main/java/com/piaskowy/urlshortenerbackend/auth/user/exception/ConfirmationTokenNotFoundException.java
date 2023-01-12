package com.piaskowy.urlshortenerbackend.auth.user.exception;

import com.piaskowy.urlshortenerbackend.globalException.BaseNotFoundException;

public class ConfirmationTokenNotFoundException extends BaseNotFoundException {
    public ConfirmationTokenNotFoundException(final String message) {
        super(message);
    }
}
