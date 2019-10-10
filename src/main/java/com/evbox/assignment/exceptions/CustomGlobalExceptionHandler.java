package com.evbox.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Exception handler to convert into HTTP responses
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex) {

        CustomErrorResponse error = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({StationNotAvailableException.class, SessionStateException.class})
    public ResponseEntity<CustomErrorResponse> stateNotAvailable(Exception ex) {

        CustomErrorResponse error = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.PRECONDITION_FAILED.value(),
                ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.PRECONDITION_FAILED);

    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomErrorResponse> unknownException(Exception ex) {

        CustomErrorResponse error = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}