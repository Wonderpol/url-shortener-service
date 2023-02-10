package com.piaskowy.urlshortenerbackend.auth.exception;

import com.piaskowy.urlshortenerbackend.global.BaseExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AuthenticationExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(value = {
            BadCredentialsException.class,
            DisabledException.class
    })
    @Override
    public ResponseEntity<Object> handleForbiddenExceptions(final RuntimeException ex, final WebRequest request) {
        return super.handleForbiddenExceptions(ex, request);
    }

    @ExceptionHandler(value = {
            AuthenticationNotFoundException.class
    })
    @Override
    public ResponseEntity<Object> handleNotFoundExceptions(final RuntimeException ex, final WebRequest request) {
        return super.handleNotFoundExceptions(ex, request);
    }
}
