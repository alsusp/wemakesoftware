package com.alsusp.wemakesoftware.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(CONFLICT)
public class NotUniqueNameException extends Exception {
	
	private static final long serialVersionUID = 2L;

	public NotUniqueNameException(String message) {
		super(message);
	}
	
	public int getErrorCode() {
		return (int) serialVersionUID;
	}
}
