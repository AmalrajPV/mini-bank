package com.arabbank.neobank.transfer.service.exception;

public class CustomerProfileInactiveException extends RuntimeException {
    String message;
    public CustomerProfileInactiveException(String message) {
        super(message);
    }
}
