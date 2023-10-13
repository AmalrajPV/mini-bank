package com.arabbank.neobank.transfer.service.controller;

import com.arabbank.neobank.transfer.service.exception.CustomerProfileInactiveException;
import com.arabbank.neobank.transfer.service.exception.InsufficientBalanceException;
import com.arabbank.neobank.transfer.service.exception.UserNotFoundException;
import com.arabbank.neobank.transfer.service.model.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionHandlerController {

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleUserNotFoundException(UserNotFoundException ex){
        ErrorDto errorDto=new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        errorDto.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST));
        log.error(String.valueOf(errorDto));
        return errorDto;
    }

    @ExceptionHandler(value = InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleInsufficientBalanceException(InsufficientBalanceException ex){
        ErrorDto errorDto=new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        errorDto.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST));
        log.error(String.valueOf(errorDto));
        return errorDto;


    }
    @ExceptionHandler(value = CustomerProfileInactiveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleCustomerProfileInactiveException(CustomerProfileInactiveException ex){
        ErrorDto errorDto=new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        errorDto.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST));
        log.error(String.valueOf(errorDto));
        return errorDto;
    }
}
