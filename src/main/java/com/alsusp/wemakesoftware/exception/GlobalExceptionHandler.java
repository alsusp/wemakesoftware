package com.alsusp.wemakesoftware.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> notFoundException(NotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getErrorCode(), ex.getMessage());
		return new ResponseEntity<>(errorDetails, NOT_FOUND);
	}

	@ExceptionHandler(NotUniqueNameException.class)
	public ResponseEntity<Object> notUniqueNameException(NotUniqueNameException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getErrorCode(), ex.getMessage());
		return new ResponseEntity<>(errorDetails, CONFLICT);
	}

	@ExceptionHandler(NotValidQuantityException.class)
	public ResponseEntity<Object> notValidQuantityException(NotValidQuantityException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getErrorCode(), ex.getMessage());
		return new ResponseEntity<>(errorDetails, BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 0, ex.getMessage());
		return new ResponseEntity<>(errorDetails, BAD_REQUEST);
	}
}
