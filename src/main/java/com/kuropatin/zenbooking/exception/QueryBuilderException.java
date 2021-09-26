package com.kuropatin.zenbooking.exception;

public class QueryBuilderException extends RuntimeException {

    public QueryBuilderException(Exception e) {
        super("Cannot build query. Reason: " + e.getClass().getSimpleName() + " " + e.getMessage());
    }
}