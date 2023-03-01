package com.example.clientservice.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRequestDTOTest {

    private Validator validator;

    public ClientRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidClientRequestDTO() {
        ClientRequestDTO client = new ClientRequestDTO("John", "Doe", "012345678", "1234567890123", "123 Main St");
        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(client);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidFirstName() {
        ClientRequestDTO client = new ClientRequestDTO("", "Doe", "678965443", "1234567890123", "123 Main St");
        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(client);
        assertEquals(1, violations.size());
        assertEquals("The first name is required.", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidLastName() {
        ClientRequestDTO client = new ClientRequestDTO("John", "", "678965443", "1234567890123", "123 Main St");
        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(client);
        assertEquals(1, violations.size());
        assertEquals("The lastName is required!", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidMobileNumber() {
        ClientRequestDTO client = new ClientRequestDTO("John", "Doe", "0123456789", "1234567890123", "123 Main St");
        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(client);
        assertEquals(1, violations.size());
        assertEquals("mobile number is incorrect", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidIdNumber() {
        ClientRequestDTO client = new ClientRequestDTO("John", "Doe", "678965443", "123456789012", "123 Main St");
        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(client);
        assertEquals(1, violations.size());
        assertEquals("id number is incorrect", violations.iterator().next().getMessage());
    }

    @Test
    public void testValidClientRequestDTOWithNullAddress() {
        ClientRequestDTO client = new ClientRequestDTO("John", "Doe", "678965443", "1234567890123", null);
        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(client);
        assertTrue(violations.isEmpty());
    }

}
