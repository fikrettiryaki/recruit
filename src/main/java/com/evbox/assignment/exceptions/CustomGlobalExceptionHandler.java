package com.evbox.assignment.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SessionNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {

		CustomErrorResponse error = new CustomErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(StationNotAvailableException.class)
	public ResponseEntity<CustomErrorResponse> stationNotAvailable(Exception ex, WebRequest request) {

		CustomErrorResponse error = new CustomErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}
}