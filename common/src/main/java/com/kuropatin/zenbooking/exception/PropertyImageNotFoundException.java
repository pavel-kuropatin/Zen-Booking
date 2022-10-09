package com.kuropatin.zenbooking.exception;

public class PropertyImageNotFoundException extends RuntimeException {

    public PropertyImageNotFoundException(final Long id) {
        super(String.format("Could not find image with id: %s", id));
    }
}