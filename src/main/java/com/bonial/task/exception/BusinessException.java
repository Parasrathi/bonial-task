package com.bonial.task.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final RuntimeException runtimeException;
    private final HttpStatus statusCode;

    public BusinessException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
        runtimeException = null;
    }
    public BusinessException(RuntimeException runtimeException, String message, HttpStatus errorCode) {
        super(message);
        this.statusCode = errorCode;
        this.runtimeException = runtimeException;
    }

    public RuntimeException getRuntimeException() {
        return runtimeException;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
