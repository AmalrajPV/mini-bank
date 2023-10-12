package com.arabbank.neobank.transfer.service.controller;

import com.arabbank.neobank.transfer.service.exception.CustomerProfileInactiveException;
import com.arabbank.neobank.transfer.service.exception.InsufficientBalanceException;
import com.arabbank.neobank.transfer.service.exception.UserNotFoundException;
import com.arabbank.neobank.transfer.service.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorDto handleUserNotFoundException(UserNotFoundException ex){
        ErrorDto errorDto=new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        errorDto.setErrorCode(String.valueOf(HttpStatus.NO_CONTENT));

        return errorDto;
    }

    @ExceptionHandler(value = InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleInsufficientBalanceException(InsufficientBalanceException ex){
        ErrorDto errorDto=new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        errorDto.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST));
        return errorDto;


    }
    @ExceptionHandler(value = CustomerProfileInactiveException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorDto handleCustomerProfileInactiveException(CustomerProfileInactiveException ex){
        ErrorDto errorDto=new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        errorDto.setErrorCode(String.valueOf(HttpStatus.NO_CONTENT));
        return errorDto;
    }
}
