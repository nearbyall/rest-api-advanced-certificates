package com.epam.esm.exception;

import com.epam.esm.exception.message.ApiStatusCode;
import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

    private final String message;
    private final ApiStatusCode apiStatusCode;
    private final HttpStatus httpStatus;

    protected ApiException(String message, ApiStatusCode apiStatusCode, HttpStatus httpStatus) {
        this.message = message;
        this.apiStatusCode = apiStatusCode;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ApiStatusCode getApiStatusCode() {
        return apiStatusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
