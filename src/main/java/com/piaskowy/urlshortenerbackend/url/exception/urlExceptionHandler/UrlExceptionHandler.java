package com.piaskowy.urlshortenerbackend.url.exception.urlExceptionHandler;

import com.piaskowy.urlshortenerbackend.auth.user.model.response.ErrorResponse;
import com.piaskowy.urlshortenerbackend.url.exception.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class UrlExceptionHandler {
    @ExceptionHandler(value = {
            UrlNotFoundException.class
    })
    public ResponseEntity<Object> handleUrlNotFoundExceptions(RuntimeException ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse exceptionMessage = ErrorResponse.builder()
                .message(ex.getMessage())
                .path(requestUri)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

}
