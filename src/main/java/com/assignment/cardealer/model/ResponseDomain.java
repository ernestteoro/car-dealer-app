package com.assignment.cardealer.model;

public class ResponseDomain {
    private String message;
    public ResponseDomain(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
