package com.project.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorResponse handleConflict(RuntimeException ex, WebRequest request) {
        return new ErrorResponse("Lo sentimos, estamos presentando problemas, intente mas tarde", ex.getMessage());
    }
}