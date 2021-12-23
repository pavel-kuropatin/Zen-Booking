package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(final Long id) {
        super(MessageFormat.format("Could not find order with id: {0}", id));
    }

    public OrderNotFoundException(final String message) {
        super(message);
    }
}