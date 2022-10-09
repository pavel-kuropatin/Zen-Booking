package com.kuropatin.zenbooking.exception;

public class OrderCannotBeDeclinedException extends RuntimeException {

    public OrderCannotBeDeclinedException(final Long id) {
        super(String.format("Order with id: %s cannot be declined", id));
    }
}