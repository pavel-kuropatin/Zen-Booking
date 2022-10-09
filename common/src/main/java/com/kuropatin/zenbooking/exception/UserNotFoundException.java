package com.kuropatin.zenbooking.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final Long id) {
        super(String.format("Could not find user with id: %s", id));
    }

    public UserNotFoundException(final String login) {
        super(String.format("Could not find user with login: %s", login));
    }
}