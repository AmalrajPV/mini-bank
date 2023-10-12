package com.arabbank.neobank.transfer.service.exception;

public class InsufficientBalanceException extends RuntimeException {
    String message;
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
