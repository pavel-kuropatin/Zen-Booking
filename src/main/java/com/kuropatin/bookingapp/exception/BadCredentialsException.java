package com.kuropatin.bookingapp.exception;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super("Incorrect login or password entered. Try again");
    }
}