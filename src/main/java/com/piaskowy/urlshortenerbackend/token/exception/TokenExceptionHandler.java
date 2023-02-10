package com.piaskowy.urlshortenerbackend.token.exception;

import com.piaskowy.urlshortenerbackend.global.BaseExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class TokenExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(value = {
            TokenException.class
    })
    @Override
    public ResponseEntity<Object> handleForbiddenExceptions(final RuntimeException ex, final WebRequest request) {
        return super.handleForbiddenExceptions(ex, request);
    }
}
