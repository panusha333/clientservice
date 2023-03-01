package com.example.clientservice.exceptions;

public class RecordNotExistsException extends RuntimeException {

    public RecordNotExistsException(String message) {
        super(message);
    }
}
