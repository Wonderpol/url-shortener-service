package com.piaskowy.urlshortenerbackend.url.exception.urlExceptionHandler;

import com.piaskowy.urlshortenerbackend.global.BaseExceptionHandler;
import com.piaskowy.urlshortenerbackend.url.exception.UrlException;
import com.piaskowy.urlshortenerbackend.url.exception.UrlNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class UrlExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(value = {
            UrlNotFoundException.class
    })
    @Override
    public ResponseEntity<Object> handleNotFoundExceptions(final RuntimeException ex, final WebRequest request) {
        return super.handleNotFoundExceptions(ex, request);
    }


    @ExceptionHandler(value = {
            UrlException.class
    })
    @Override
    public ResponseEntity<Object> handleForbiddenExceptions(final RuntimeException ex, final WebRequest request) {
        return super.handleForbiddenExceptions(ex, request);
    }
}
