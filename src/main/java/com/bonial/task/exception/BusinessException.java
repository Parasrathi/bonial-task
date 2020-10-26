package com.bonial.task.exception;

public class BusinessException extends Exception {

    private final Exception exception;
    private final ExceptionErrorCode error;

    public BusinessException(ExceptionErrorCode error, Exception exception) {
        super(error.getDescription());
        this.exception = exception;
        this.error = error;
    }

    public BusinessException(ExceptionErrorCode error) {
        this.exception = null;
        this.error = error;
    }

    public Exception getException() {
        return exception;
    }

    public ExceptionErrorCode getError() {
        return error;
    }
}
