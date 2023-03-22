package com.sample.retailstore.exception;

import com.sample.retailstore.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.sample.retailstore.constants.MessageConstants.PROCESSING_ERROR_MSG;
import static com.sample.retailstore.constants.MessageConstants.VALIDATION_ERROR_MSG;

/**
 * @author irfan.nagoo
 */

@ControllerAdvice
public class RetailStoreControllerAdvice {

    private final Logger LOGGER = LoggerFactory.getLogger(RetailStoreControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException me) {
        LOGGER.error("Validation error occurred: ", me);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), VALIDATION_ERROR_MSG);
        me.getBindingResult().getFieldErrors()
                .forEach(e -> response.getErrors().add(new ErrorResponse.ValidationError(e.getField(), e.getDefaultMessage())));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException nfe) {
        LOGGER.error("Record not found: ", nfe);
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.name(), nfe.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ActionNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleNotAllowedException(ActionNotAllowedException nae) {
        LOGGER.error("Action Not allowed: ", nae);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), nae.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException nfe) {
        LOGGER.error("Bad request parameter: ", nfe);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), nfe.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        LOGGER.error("Processing error occurred: ", e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(),
                PROCESSING_ERROR_MSG));
    }

}
