package com.example.clientservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorMessageTest {

    @Test
    public void testConstructor() {
        String expectedMessage = "Error message";
        ErrorMessage errorMessage = new ErrorMessage(expectedMessage);
        assertEquals(expectedMessage, errorMessage.getMessage());
    }

    @Test
    public void testSetter() {
        String expectedMessage = "Error message";
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(expectedMessage);
        assertEquals(expectedMessage, errorMessage.getMessage());
    }
}
