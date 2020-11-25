package com.alsusp.wemakesoftware.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
	
    private LocalDateTime timestamp;
    private int errorCode;
    private String errorDescription;

    public ErrorDetails(LocalDateTime timestamp, int errorCode, String errorDescription) {
        super();
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
