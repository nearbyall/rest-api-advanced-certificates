package com.epam.esm.validation;

import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.message.ApiStatusCode;
import com.epam.esm.exception.message.MessageFactory;
import com.epam.esm.service.dto.certificate.CertificateExclusivePatchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Validates possible error occurrences in the DTO objects given by the user
 */
@Component
public class Validator {

    private final MessageFactory messageFactory;

    @Autowired
    public Validator(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    /**
     * Checks errors in {@link BindingResult}
     *
     * @param result        contains errors from the validation
     * @param apiStatusCode status code for the exception if the result has any errors
     */
    public void checkErrorsInBindingResult(BindingResult result, ApiStatusCode apiStatusCode) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();

            String errorString = errors
                    .stream()
                    .filter(FieldError.class::isInstance)
                    .map(FieldError.class::cast)
                    .map(error -> error.getField() + " " + error.getDefaultMessage() + ", ")
                    .collect(Collectors.joining());

            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(messageFactory.getInvalidData()).append(": ").append(errorString);
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length()).append(".");

            throw new BadRequestException(errorMessage.toString(), apiStatusCode, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Used for the exclusive patch (price or duration), checks whether {@link CertificateExclusivePatchDTO} has only one field
     *
     * @param certificate DTO with 2 possible fields
     */
    public void checkExclusivePatchHavingOneArgument(CertificateExclusivePatchDTO certificate) {
        if ((certificate.getPrice() == null) == (certificate.getDuration() == null)) {
            throw new BadRequestException(
                    messageFactory.getInvalidDataExclusivePatch(),
                    ApiStatusCode.CERTIFICATE_BAD_REQUEST,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
