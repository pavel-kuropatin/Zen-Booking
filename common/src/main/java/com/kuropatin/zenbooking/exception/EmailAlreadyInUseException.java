package com.kuropatin.zenbooking.exception;

public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException(final String email) {
        super(String.format("Email %s already in use", email));
    }
}