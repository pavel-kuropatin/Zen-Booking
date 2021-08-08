package com.kuropatin.bookingapp.exception;

import java.text.MessageFormat;

public class OrderCannotBeDeclinedException extends RuntimeException {

    public OrderCannotBeDeclinedException(Long id) {
        super(MessageFormat.format("Оrder with id: {0} cannot be declined", id));
    }
}