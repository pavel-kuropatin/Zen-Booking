package com.kuropatin.zenbooking.exception;

public class OrderCannotBeAcceptedException extends RuntimeException {

    public OrderCannotBeAcceptedException(final Long id) {
        super(String.format("Order with id: %s cannot be accepted", id));
    }
}