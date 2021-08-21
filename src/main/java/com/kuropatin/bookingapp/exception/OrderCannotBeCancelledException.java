package com.kuropatin.bookingapp.exception;

import java.text.MessageFormat;

public class OrderCannotBeCancelledException extends RuntimeException {

    public OrderCannotBeCancelledException(Long id) {
        super(MessageFormat.format("Order with id: {0} cannot be cancelled", id));
    }
}