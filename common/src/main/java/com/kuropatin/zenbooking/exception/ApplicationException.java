package com.kuropatin.zenbooking.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(final Throwable e) {
        super(e);
    }
}