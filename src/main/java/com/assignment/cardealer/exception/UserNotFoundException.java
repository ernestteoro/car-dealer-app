package com.assignment.cardealer.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super(String.format("User name  %s  was not found", username));
    }
}
