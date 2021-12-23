package com.kuropatin.zenbooking.exception;

public class AppException extends RuntimeException {

    public AppException(final Exception e) {
        super(e.getClass() + " " + e.getMessage());
    }
}