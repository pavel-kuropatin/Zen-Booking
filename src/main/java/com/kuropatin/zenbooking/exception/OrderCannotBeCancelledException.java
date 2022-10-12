package com.kuropatin.zenbooking.exception;

public class OrderCannotBeCancelledException extends RuntimeException {

    public OrderCannotBeCancelledException(final Long id) {
        super(String.format("Order with id: %s cannot be cancelled", id));
    }
}