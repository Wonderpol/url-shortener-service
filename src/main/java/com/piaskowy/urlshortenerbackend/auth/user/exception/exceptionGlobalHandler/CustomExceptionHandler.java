package com.piaskowy.urlshortenerbackend.auth.user.exception.exceptionGlobalHandler;

import com.piaskowy.urlshortenerbackend.auth.user.exception.ConfirmationTokenNotFoundException;
import com.piaskowy.urlshortenerbackend.auth.user.exception.EmailIsAlreadyConfirmedException;
import com.piaskowy.urlshortenerbackend.auth.user.exception.TokenExpiredException;
import com.piaskowy.urlshortenerbackend.auth.user.exception.UserAlreadyExistsException;
import com.piaskowy.urlshortenerbackend.auth.user.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            UserAlreadyExistsException.class,
            TokenExpiredException.class,
            EmailIsAlreadyConfirmedException.class,
            ConfirmationTokenNotFoundException.class
    })
    public ResponseEntity<Object> handleUserAlreadyExistsException(RuntimeException ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse exceptionMessage = ErrorResponse.builder()
                .message(ex.getMessage())
                .path(requestUri)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
    }

}
