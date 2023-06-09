package com.assignment.cardealer.exception;

public class DealerNotFoundException extends RuntimeException {
    public DealerNotFoundException(String name) {
        super(String.format("%s was not found", name));
    }
}
