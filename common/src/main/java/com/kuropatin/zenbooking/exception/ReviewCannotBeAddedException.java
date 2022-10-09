package com.kuropatin.zenbooking.exception;

public class ReviewCannotBeAddedException extends RuntimeException {

    public ReviewCannotBeAddedException(final Long id) {
        super(String.format("Review cannot be added to order with id: %s", id));
    }
}