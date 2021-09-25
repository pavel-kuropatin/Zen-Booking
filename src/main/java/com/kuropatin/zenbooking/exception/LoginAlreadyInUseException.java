package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class LoginAlreadyInUseException extends RuntimeException {

    public LoginAlreadyInUseException(String login) {
        super(MessageFormat.format("Login {0} already in use", login));
    }
}