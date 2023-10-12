package com.arabbank.neobank.transfer.service.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class TransferException extends RuntimeException {
    public TransferException() {
        super();
    }

    public TransferException(String msg) {
        super(msg);
    }
}
