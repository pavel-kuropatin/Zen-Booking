package com.kuropatin.zenbooking.exception;

public class PropertyNotFoundException extends RuntimeException {

    public PropertyNotFoundException(final Long id) {
        super(String.format("Could not find property with id: %s", id));
    }
}