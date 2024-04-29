package com.example.registerApp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RegisterRequestExceptionHandler {

    @ExceptionHandler(value = {RegisterRequestException.class})
    protected ResponseEntity<Object> handleApiRequestException(RegisterRequestException apiRegisterRequestException){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        RegisterException apiRegisterException = new RegisterException(
                apiRegisterRequestException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("GMT+2"))
        );

        return new ResponseEntity<>(apiRegisterException,httpStatus);
    }
}
