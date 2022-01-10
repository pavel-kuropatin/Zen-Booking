package com.kuropatin.zenbooking.exception;

import java.text.MessageFormat;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(final Long id) {
        super(MessageFormat.format("Could not find review with id: {0}", id));
    }
}