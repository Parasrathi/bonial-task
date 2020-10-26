package com.bonial.task.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionErrorCode {

    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR,"Application Logic Error"),
    ENTITY_NOT_FOUND("ENTITY_NOT_FOUND", HttpStatus.NOT_FOUND,"Resource Not Found"),
    DECIMAL_PARSING_ERROR("DECIMAL_PARSING_ERROR", HttpStatus.INTERNAL_SERVER_ERROR,"Decimal parsing error"),
    JSON_MAPPING_ERROR("JSON_MAPPING_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "JSON object mapping error"),
    JSON_OBJECTS_NOT_FOUND("JSON_OBJECTS_NOT_FOUND", HttpStatus.INTERNAL_SERVER_ERROR, "Restaurant JSON Objects not found");


    private String code;
    private HttpStatus status;
    private String description;

    ExceptionErrorCode(String code, HttpStatus status, String description) {
        this.code = code;
        this.status = status;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }
}
