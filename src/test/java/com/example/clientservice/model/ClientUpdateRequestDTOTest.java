package com.example.clientservice.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientUpdateRequestDTOTest {

    private Validator validator;

    public ClientUpdateRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidClientUpdateRequestDTO() {
        ClientUpdateRequestDTO client = new ClientUpdateRequestDTO("John", "Doe", "678965443", "1234567890123", "123 Main St");
        Set<ConstraintViolation<ClientUpdateRequestDTO>> violations = validator.validate(client);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidMobileNumber() {
        ClientUpdateRequestDTO client = new ClientUpdateRequestDTO("John", "Doe", "0123456789", "1234567890123", "123 Main St");
        Set<ConstraintViolation<ClientUpdateRequestDTO>> violations = validator.validate(client);
        assertEquals(1, violations.size());
        assertEquals("mobile number is incorrect", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidIdNumber() {
        ClientUpdateRequestDTO client = new ClientUpdateRequestDTO("John", "Doe", "678965443", "123456789012", "123 Main St");
        Set<ConstraintViolation<ClientUpdateRequestDTO>> violations = validator.validate(client);
        assertEquals(1, violations.size());
        assertEquals("id number is incorrect", violations.iterator().next().getMessage());
    }

    @Test
    public void testValidClientUpdateRequestDTOWithNullFields() {
        ClientUpdateRequestDTO client = new ClientUpdateRequestDTO(null, null, null, null, null);
        Set<ConstraintViolation<ClientUpdateRequestDTO>> violations = validator.validate(client);
        assertTrue(violations.isEmpty());
    }

}
