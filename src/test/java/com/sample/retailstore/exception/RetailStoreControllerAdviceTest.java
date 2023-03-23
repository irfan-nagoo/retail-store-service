package com.sample.retailstore.exception;

import com.sample.retailstore.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Executable;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetailStoreControllerAdviceTest {

    @InjectMocks
    private RetailStoreControllerAdvice controllerAdvice;

    @Test
    void handleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        MethodParameter methodParameter = mock(MethodParameter.class);
        when(methodParameter.getExecutable()).thenReturn(mock(Executable.class));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);
        FieldError fieldError = mock(FieldError.class);
        when(fieldError.getField()).thenReturn("name");
        when(fieldError.getDefaultMessage()).thenReturn("Invalid");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        ResponseEntity<ErrorResponse> response = controllerAdvice.handleMethodArgumentNotValidException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("name", response.getBody().getErrors().get(0).getField());
    }

    @Test
    void handleRecordNotFoundException() {
        RecordNotFoundException exception = new RecordNotFoundException("Record Not Found");
        ResponseEntity<ErrorResponse> response = controllerAdvice.handleRecordNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Record Not Found", response.getBody().getMessage());
    }

    @Test
    void handleActionNotAllowedException() {
        ActionNotAllowedException exception = new ActionNotAllowedException("Cancel Not Allowed");
        ResponseEntity<ErrorResponse> response = controllerAdvice.handleActionNotAllowedException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cancel Not Allowed", response.getBody().getMessage());
    }

    @Test
    void handleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Input argument Invalid");
        ResponseEntity<ErrorResponse> response = controllerAdvice.handleIllegalArgumentException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Input argument Invalid", response.getBody().getMessage());
    }

    @Test
    void handleException() {
        Exception exception = new Exception("Generic error");
        ResponseEntity<ErrorResponse> response = controllerAdvice.handleException(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Processing Error has Occurred", response.getBody().getMessage());
    }
}