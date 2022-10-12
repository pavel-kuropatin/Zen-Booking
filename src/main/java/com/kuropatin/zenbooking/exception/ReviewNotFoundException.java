package com.kuropatin.zenbooking.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(final Long id) {
        super(String.format("Could not find review with id: %s", id));
    }
}