package com.epam.esm.exception.handler;

import com.epam.esm.exception.ApiException;
import com.epam.esm.exception.ApiResponse;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.message.MessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * REST Controller advice for the API exceptions interception
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

    private final MessageFactory messageFactory;

    @Autowired
    public ApplicationExceptionHandler(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class, BadRequestException.class})
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), ex.getApiStatusCode().getValue());

        return new ResponseEntity<>(apiResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<ApiResponse> handleNoHandlerException(NoHandlerFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(messageFactory.getNotFound(), 404);

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(Exception ex) {
        ApiResponse apiResponse = new ApiResponse(messageFactory.getInvalidData(), 400);

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        ApiResponse apiResponse = new ApiResponse(messageFactory.getServerError(), 500);

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

