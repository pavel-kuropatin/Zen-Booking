package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class PropertyImageNotFoundException extends RuntimeException {

    public PropertyImageNotFoundException(Long id) {
        super(MessageFormat.format("Could not find image with id: {0}", id));
    }
}