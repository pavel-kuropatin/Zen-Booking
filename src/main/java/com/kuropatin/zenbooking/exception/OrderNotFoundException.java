package com.kuropatin.zenbooking.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(final Long id) {
        super(String.format("Could not find order with id: %s", id));
    }

    public OrderNotFoundException(final String message) {
        super(message);
    }
}