package com.alsusp.wemakesoftware.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDetails {
	
    private LocalDateTime timestamp;
    
    @JsonProperty("error_code")
    private int errorCode;
    
    @JsonProperty("error_description")
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
