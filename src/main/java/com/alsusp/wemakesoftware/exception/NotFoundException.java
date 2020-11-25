package com.alsusp.wemakesoftware.exception;  

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}
	
	public int getErrorCode() {
		return (int) serialVersionUID;
	}
}
