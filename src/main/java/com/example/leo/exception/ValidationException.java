package com.example.leo.exception;

import java.util.List;

public class ValidationException extends RuntimeException{
    public List<String> errorMessages;

    public ValidationException(List<String> errorMessages) {
        super("Validation failed");
        this.errorMessages = errorMessages;
    }
    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
