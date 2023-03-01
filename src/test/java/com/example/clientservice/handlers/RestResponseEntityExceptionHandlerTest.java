package com.example.clientservice.handlers;

import com.example.clientservice.exceptions.InvalidIdNumberException;
import com.example.clientservice.exceptions.RecordExistsException;
import com.example.clientservice.exceptions.RecordNotExistsException;
import com.example.clientservice.exceptions.SearchParamNotFoundException;
import com.example.clientservice.model.ErrorMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestResponseEntityExceptionHandlerTest {

    @InjectMocks
    RestResponseEntityExceptionHandler handler ;

    @Test
    public void handleRecordNotExistsExceptionTest() {
        RecordNotExistsException ex = new RecordNotExistsException("Record not found.");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = handler.handleRecordNotExistsException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(new ErrorMessage("Record not found."), response.getBody());
    }

    @Test
    public void handleRecordExistsExceptionTest() {
        RecordExistsException ex = new RecordExistsException("Record already exists.");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = handler.handleRecordExistsException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(new ErrorMessage("Record already exists."), response.getBody());
    }

    @Test
    public void handleSearchParamNotFoundExceptionTest() {
        SearchParamNotFoundException ex = new SearchParamNotFoundException("Search parameter not found.");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = handler.handleSearchParamNotFoundException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(new ErrorMessage("Search parameter not found."), response.getBody());
    }

    @Test
    public void handleInvalidIdNumberExceptionTest() {
        InvalidIdNumberException ex = new InvalidIdNumberException("Invalid ID number.");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = handler.handleInvalidIdNumberException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(new ErrorMessage("Invalid ID number."), response.getBody());
    }



}
