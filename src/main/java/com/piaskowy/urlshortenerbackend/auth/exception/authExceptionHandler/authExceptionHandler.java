package com.piaskowy.urlshortenerbackend.auth.exception.authExceptionHandler;

import com.piaskowy.urlshortenerbackend.auth.exception.ConfirmationTokenNotFoundException;
import com.piaskowy.urlshortenerbackend.auth.exception.EmailIsAlreadyConfirmedException;
import com.piaskowy.urlshortenerbackend.auth.exception.TokenExpiredException;
import com.piaskowy.urlshortenerbackend.auth.exception.UserAlreadyExistsException;
import com.piaskowy.urlshortenerbackend.auth.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class authExceptionHandler {
    @ExceptionHandler(value = {
            UserAlreadyExistsException.class,
            TokenExpiredException.class,
            EmailIsAlreadyConfirmedException.class,
            ConfirmationTokenNotFoundException.class
    })
    public ResponseEntity<Object> handleUserAuthExceptions(RuntimeException ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse exceptionMessage = ErrorResponse.builder()
                .message(ex.getMessage())
                .path(requestUri)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse exceptionMessage = ErrorResponse.builder()
                .message(errors.values().toString())
                .path(requestUri)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
    }

}
