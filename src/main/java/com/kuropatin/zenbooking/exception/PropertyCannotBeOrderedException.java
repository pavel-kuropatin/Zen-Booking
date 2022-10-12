package com.kuropatin.zenbooking.exception;

public class PropertyCannotBeOrderedException extends RuntimeException {

    public PropertyCannotBeOrderedException(final Long id) {
        super(String.format("Could not order property with id: %s", id));
    }
}