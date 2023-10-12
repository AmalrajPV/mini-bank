package com.arabbank.neobank.transfer.service.model.dto;

public class ErrorDto {
    private String message;
    private String errorCode;


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
