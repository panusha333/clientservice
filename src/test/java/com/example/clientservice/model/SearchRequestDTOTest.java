package com.example.clientservice.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchRequestDTOTest {

    private Validator validator;

    public SearchRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidSearchRequestDTO() {
        SearchRequestDTO search = new SearchRequestDTO("John", "678965443", "1234567890123");
        Set<ConstraintViolation<SearchRequestDTO>> violations = validator.validate(search);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidMobileNumber() {
        SearchRequestDTO search = new SearchRequestDTO("John", "0123456789", "1234567890123");
        Set<ConstraintViolation<SearchRequestDTO>> violations = validator.validate(search);
        assertEquals(1, violations.size());
        assertEquals("mobile number is incorrect", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidIdNumber() {
        SearchRequestDTO search = new SearchRequestDTO("John", "678965443", "123456789012");
        Set<ConstraintViolation<SearchRequestDTO>> violations = validator.validate(search);
        assertEquals(1, violations.size());
        assertEquals("id number is incorrect", violations.iterator().next().getMessage());
    }

    @Test
    public void testValidSearchRequestDTOWithNullFields() {
        SearchRequestDTO search = new SearchRequestDTO(null, null, null);
        Set<ConstraintViolation<SearchRequestDTO>> violations = validator.validate(search);
        assertTrue(violations.isEmpty());
    }

}
