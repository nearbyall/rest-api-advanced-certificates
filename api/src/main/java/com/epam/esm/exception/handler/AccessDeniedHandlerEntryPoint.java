package com.epam.esm.exception.handler;

import com.epam.esm.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.epam.esm.exception.message.ApiStatusCode.FORBIDDEN_EXCEPTION;

@Component
public class AccessDeniedHandlerEntryPoint implements AccessDeniedHandler {

    private static final String ENCODING = "UTF-8";
    private static final String NO_RIGHTS_MESSAGE = "controllerMessage.noRights";
    private final MessageSource messageSource;

    @Autowired
    public AccessDeniedHandlerEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(ENCODING);
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());

        List<String> details = Collections.singletonList(messageSource.getMessage(NO_RIGHTS_MESSAGE, new String[]{}, httpServletRequest.getLocale()));
        httpServletResponse.getWriter()
                .write(String.valueOf(new ObjectMapper()
                        .writeValueAsString(new ErrorResponse(FORBIDDEN_EXCEPTION.toString(), details))));
    }

}
