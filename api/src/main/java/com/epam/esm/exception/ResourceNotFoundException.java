package com.epam.esm.exception;

import com.epam.esm.exception.message.ApiStatusCode;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message, ApiStatusCode apiStatusCode, HttpStatus httpStatus) {
        super(message, apiStatusCode, httpStatus);
    }
}
