package com.example.digicore.exception;

public class ApiConflictException extends RuntimeException{
    public ApiConflictException(String message) {
        super(message);
    }
}
