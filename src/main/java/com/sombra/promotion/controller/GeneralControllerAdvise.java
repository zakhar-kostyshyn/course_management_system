package com.sombra.promotion.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GeneralControllerAdvise {

    @Value("${app.controller-advise.generic-error-message}")
    private String genericErrorMessage;

    @Value("${app.controller-advise.show-stacktrace}")
    private boolean showStacktrace;

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleAny(Throwable error, HttpServletRequest request, HttpServletResponse response) {

        log.error("Processing "+error, error);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseStatus annotation = AnnotationUtils.findAnnotation(error.getClass(), ResponseStatus.class);
        HttpStatus statusCode;
        if (annotation != null) statusCode = annotation.code();
        else statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        String message;
        if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR) message = genericErrorMessage;
        else message = error.getMessage();

        StackTraceElement[] stackTrace = null;
        if (showStacktrace) stackTrace = error.getStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(request.getServletPath(), message, stackTrace);
        return new ResponseEntity<>(errorResponse, headers, statusCode);

    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ErrorResponse {

    @Nullable private String path;
    @Nullable private String message;
    @Nullable private StackTraceElement[] stackTrace;

}
