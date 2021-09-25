package com.kuropatin.zenbooking.exception;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super("Incorrect login or password entered. Try again");
    }
}