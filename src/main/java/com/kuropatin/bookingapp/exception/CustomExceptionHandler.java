package com.kuropatin.bookingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> userNotFoundHandler(UserNotFoundException e) {
        return throwCustomNotFoundException(e);
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    protected ResponseEntity<Object> propertyNotFoundHandler(PropertyNotFoundException e) {
        return throwCustomNotFoundException(e);
    }

    @ExceptionHandler(PropertyImageNotFoundException.class)
    protected ResponseEntity<Object> propertyImageNotFoundHandler(PropertyImageNotFoundException e) {
        return throwCustomNotFoundException(e);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<Object> orderNotFoundHandler(OrderNotFoundException e) {
        return throwCustomNotFoundException(e);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    protected ResponseEntity<Object> reviewNotFoundHandler(ReviewNotFoundException e) {
        return throwCustomNotFoundException(e);
    }

    @ExceptionHandler(InsufficientMoneyAmountException.class)
    protected ResponseEntity<Object> insufficientMoneyAmountHandler(InsufficientMoneyAmountException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.PAYMENT_REQUIRED.value());
        body.put("error", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<Object> reviewNotFoundHandler(SQLException e) {
        return throwCustomSQLException(e);
    }

    private ResponseEntity<Object> throwCustomNotFoundException(RuntimeException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> throwCustomSQLException(Exception e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}