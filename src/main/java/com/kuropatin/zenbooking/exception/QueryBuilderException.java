package com.kuropatin.zenbooking.exception;

public class QueryBuilderException extends RuntimeException {

    public QueryBuilderException(final Exception e) {
        super("Cannot build query. Reason: " + e.getClass().getSimpleName() + " " + e.getMessage());
    }
}