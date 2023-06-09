package com.assignment.cardealer.exception;

public class ListingNotFoundException extends RuntimeException {
    public ListingNotFoundException(Long id) {
        super(String.format("Listing with id %s  was not found", id.toString()));
    }
}
