package com.epam.esm.exception;

import java.util.List;

public class ErrorResponse {
    private final String errorCode;
    private final List<String> errorMessages;

    public ErrorResponse(String errorCode, List<String> errorMessages) {
        this.errorCode = errorCode;
        this.errorMessages = errorMessages;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
