package com.piaskowy.urlshortenerbackend.globalException;

public abstract class BaseForbiddenException extends RuntimeException {
    public BaseForbiddenException() {
        super();
    }

    public BaseForbiddenException(final String message) {
        super(message);
    }
}
