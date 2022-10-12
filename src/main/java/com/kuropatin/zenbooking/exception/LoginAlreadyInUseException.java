package com.kuropatin.zenbooking.exception;

public class LoginAlreadyInUseException extends RuntimeException {

    public LoginAlreadyInUseException(final String login) {
        super(String.format("Login %s already in use", login));
    }
}