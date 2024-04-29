package com.example.registerApp.exception;

public class RegisterRequestException extends RuntimeException{


    public RegisterRequestException(String message){
        super(message);
    }

    public RegisterRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
