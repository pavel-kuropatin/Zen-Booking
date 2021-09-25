package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class PropertyNotFoundException extends RuntimeException {

    public PropertyNotFoundException(Long id) {
        super(MessageFormat.format("Could not find property with id: {0}", id));
    }
}