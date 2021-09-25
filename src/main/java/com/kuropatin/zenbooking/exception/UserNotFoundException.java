package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(MessageFormat.format("Could not find user with id: {0}", id));
    }

    public UserNotFoundException(String login) {
        super(MessageFormat.format("Could not find user with login: {0}", login));
    }
}