package com.alsusp.wemakesoftware.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(BAD_REQUEST)
public class NotValidQuantityException extends Exception {
	
	private static final long serialVersionUID = 3L;

	public NotValidQuantityException(String errorMessage) {
		super(errorMessage);
	}
	
	public int getErrorCode() {
		return (int) serialVersionUID;
	}
}
