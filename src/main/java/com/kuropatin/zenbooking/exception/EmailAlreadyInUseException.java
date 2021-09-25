package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException(String email) {
        super(MessageFormat.format("Email {0} already in use", email));
    }
}