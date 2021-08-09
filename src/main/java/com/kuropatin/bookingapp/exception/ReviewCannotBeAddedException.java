package com.kuropatin.bookingapp.exception;

import java.text.MessageFormat;

public class ReviewCannotBeAddedException extends RuntimeException {

    public ReviewCannotBeAddedException(Long id) {
        super(MessageFormat.format("Review cannot be added to order with id: {0}", id));
    }
}