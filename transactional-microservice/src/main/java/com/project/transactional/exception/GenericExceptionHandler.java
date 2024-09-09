package com.project.transactional.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorResponse handleError(RuntimeException ex, WebRequest request) {
        return new ErrorResponse("Lo sentimos, estamos presentando problemas, intente mas tarde", ex.getMessage());
    }

    @ExceptionHandler(NotBalanceException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorResponse handleNotBalanceException(NotBalanceException ex, WebRequest request) {
        return new ErrorResponse("Los sentimos, no tienes el saldo suficiente para continuar.", ex.getMessage());
    }
}