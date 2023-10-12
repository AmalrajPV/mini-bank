package com.arabbank.neobank.transfer.service.exception;


public class UserNotFoundException extends RuntimeException {
//    private String errorMessage;

    public UserNotFoundException(String message) {
        super(message);

    }
}