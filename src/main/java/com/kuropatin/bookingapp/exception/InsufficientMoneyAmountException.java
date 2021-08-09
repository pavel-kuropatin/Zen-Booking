package com.kuropatin.bookingapp.exception;

public class InsufficientMoneyAmountException extends RuntimeException {

    public InsufficientMoneyAmountException() {
        super("Insufficient money amount");
    }
}