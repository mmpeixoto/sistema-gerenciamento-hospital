package com.iff.sistema_gerenciamento_hospital.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MapeadorExcecao {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorBody handleNotFoundException(NotFoundException e) {
        return new ErrorBody(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorBody handlePreConditionFailedException(BadRequestException e) {
        return new ErrorBody(HttpStatus.PRECONDITION_FAILED, e.getMessage());
    }
}
