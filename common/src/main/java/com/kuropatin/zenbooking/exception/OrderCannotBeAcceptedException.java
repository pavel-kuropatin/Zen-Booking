package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class OrderCannotBeAcceptedException extends RuntimeException {

    public OrderCannotBeAcceptedException(final Long id) {
        super(MessageFormat.format("Order with id: {0} cannot be accepted", id));
    }
}