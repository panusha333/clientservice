package com.example.clientservice.exceptions;

public class InvalidIdNumberException extends RuntimeException {

    public InvalidIdNumberException(String message) {
        super(message);
    }
}
