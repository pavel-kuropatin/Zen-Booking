package com.kuropatin.bookingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> userNotFoundHandler(UserNotFoundException e) {
        return throwCustomException(e);
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    protected ResponseEntity<Object> propertyNotFoundHandler(PropertyNotFoundException e) {
        return throwCustomException(e);
    }

    private ResponseEntity<Object> throwCustomException(RuntimeException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}