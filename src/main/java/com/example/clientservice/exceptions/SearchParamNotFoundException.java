package com.example.clientservice.exceptions;

public class SearchParamNotFoundException extends RuntimeException {

    public SearchParamNotFoundException(String message) {
        super(message);
    }
}
