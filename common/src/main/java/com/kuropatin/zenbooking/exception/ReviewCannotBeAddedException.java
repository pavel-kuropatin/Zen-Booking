package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class ReviewCannotBeAddedException extends RuntimeException {

    public ReviewCannotBeAddedException(final Long id) {
        super(MessageFormat.format("Review cannot be added to order with id: {0}", id));
    }
}