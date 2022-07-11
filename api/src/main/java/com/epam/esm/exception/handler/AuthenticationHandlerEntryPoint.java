package com.epam.esm.exception.handler;

import com.epam.esm.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.epam.esm.exception.message.ApiStatusCode.UNAUTHORIZED_EXCEPTION;

@Component
public class AuthenticationHandlerEntryPoint implements AuthenticationEntryPoint {
    private static final String ENCODING = "UTF-8";
    private static final String UNAUTHORIZED_MESSAGE = "controllerMessage.unauthorized";
    private final MessageSource messageSource;

    @Autowired
    public AuthenticationHandlerEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException {
        httpServletResponse.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(ENCODING);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        List<String> details = Collections.singletonList(messageSource.getMessage(UNAUTHORIZED_MESSAGE, new String[]{}, httpServletRequest.getLocale()));
        httpServletResponse.getWriter()
                .write(String.valueOf(new ObjectMapper()
                        .writeValueAsString(new ErrorResponse(UNAUTHORIZED_EXCEPTION.toString(), details))));
    }
}
