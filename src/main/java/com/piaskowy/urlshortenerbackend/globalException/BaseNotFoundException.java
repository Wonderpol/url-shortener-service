package com.piaskowy.urlshortenerbackend.globalException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public abstract class BaseNotFoundException extends RuntimeException {
    public BaseNotFoundException() {
        super();
    }

    public BaseNotFoundException(final String message) {
        super(message);
    }
}
