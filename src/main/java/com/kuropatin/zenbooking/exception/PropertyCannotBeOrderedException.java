package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class PropertyCannotBeOrderedException extends RuntimeException {

    public PropertyCannotBeOrderedException(Long id) {
        super(MessageFormat.format("Could not order property with id: {0}", id));
    }
}