package com.assignment.cardealer.exception;

public class TierLimitException extends RuntimeException {

    public TierLimitException(String name) {
        super(String.format("%s has reached his tier limit", name));
    }
}

