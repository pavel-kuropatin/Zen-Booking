package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class OrderCannotBeDeclinedException extends RuntimeException {

    public OrderCannotBeDeclinedException(final Long id) {
        super(MessageFormat.format("Order with id: {0} cannot be declined", id));
    }
}