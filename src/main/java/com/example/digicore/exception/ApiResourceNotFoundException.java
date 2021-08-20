package com.example.digicore.exception;

public class ApiResourceNotFoundException extends RuntimeException{
    public ApiResourceNotFoundException(String message) {
        super(message);
    }
}
