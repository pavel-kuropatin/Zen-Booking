package com.kuropatin.zenbooking.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(final Exception e) {
        super(e.getClass() + " " + e.getMessage());
    }

    public ApplicationException(final String message) {
        super(message);
    }
}