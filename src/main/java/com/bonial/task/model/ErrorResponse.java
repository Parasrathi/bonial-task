package com.bonial.task.model;

public class ErrorResponse {

    private String title;
    private String message;
    private int statusCode;

    public ErrorResponse() {}

    public ErrorResponse(String title, String message, int statusCode) {
        this.title = title;
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
