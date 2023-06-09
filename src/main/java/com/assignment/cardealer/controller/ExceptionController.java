package com.assignment.cardealer.controller;

import com.assignment.cardealer.exception.DealerNotFoundException;
import com.assignment.cardealer.exception.ListingNotFoundException;
import com.assignment.cardealer.exception.TierLimitException;
import com.assignment.cardealer.model.ResponseDomain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DealerNotFoundException.class)
    public ResponseEntity<ResponseDomain> handleNotFoundException(DealerNotFoundException ex) {
        return new ResponseEntity<>(new ResponseDomain(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ListingNotFoundException.class)
    public ResponseEntity<ResponseDomain> handleNotFoundException(ListingNotFoundException ex) {
        return new ResponseEntity<>(new ResponseDomain(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TierLimitException.class)
    public ResponseEntity<ResponseDomain> handleNotFoundException(TierLimitException ex) {
        return new ResponseEntity<>(new ResponseDomain(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDomain> handleException(Exception ex) {
        return new ResponseEntity<>(new ResponseDomain(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}