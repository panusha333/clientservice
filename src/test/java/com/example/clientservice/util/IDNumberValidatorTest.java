package com.example.clientservice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IDNumberValidatorTest {

    @Test
    void testValidIDNumber() {
        Assertions.assertTrue(IDNumberValidator.validateIDNumber("7806062507085"));
    }

    @Test
    void testInvalidMonth() {
        Assertions.assertFalse(IDNumberValidator.validateIDNumber("9700135010082"));
    }

    @Test
    void testInvalidDate() {
        Assertions.assertFalse(IDNumberValidator.validateIDNumber("9701010090082"));
    }

    @Test
    void testInvalidCitizenship() {
        Assertions.assertFalse(IDNumberValidator.validateIDNumber("9701015010182"));
    }

    @Test
    void testInvalidCheckDigit() {
        Assertions.assertFalse(IDNumberValidator.validateIDNumber("9701015010083"));
    }

    @Test
    void testInvalidLength() {
        Assertions.assertFalse(IDNumberValidator.validateIDNumber("97010150100821"));
    }
}
