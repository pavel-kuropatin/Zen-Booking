package com.kuropatin.bookingapp.exception;

import io.jsonwebtoken.JwtException;
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
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    protected ResponseEntity<Object> propertyNotFoundHandler(PropertyNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyImageNotFoundException.class)
    protected ResponseEntity<Object> propertyImageNotFoundHandler(PropertyImageNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<Object> orderNotFoundHandler(OrderNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    protected ResponseEntity<Object> reviewNotFoundHandler(ReviewNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientMoneyAmountException.class)
    protected ResponseEntity<Object> insufficientMoneyAmountHandler(InsufficientMoneyAmountException e) {
        return throwCustomException(e, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<Object> sqlExceptionHandler(SQLException e) {
        return throwCustomException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<Object> jwtExceptionHandler(JwtException e) {
        return throwCustomException(e, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<Object> throwCustomException(Exception e, HttpStatus httpStatus) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", httpStatus.value());
        body.put("error", e.getMessage());
        return new ResponseEntity<>(body, httpStatus);
    }
}