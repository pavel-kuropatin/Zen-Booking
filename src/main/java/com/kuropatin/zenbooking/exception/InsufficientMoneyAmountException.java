package com.kuropatin.zenbooking.exception;

public class InsufficientMoneyAmountException extends RuntimeException {

    public InsufficientMoneyAmountException() {
        super("Insufficient money amount");
    }
}