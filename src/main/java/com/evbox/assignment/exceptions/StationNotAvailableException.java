package com.evbox.assignment.exceptions;

/**
 * Exception for not available station
 */
public class StationNotAvailableException extends RuntimeException {

    public StationNotAvailableException(String id) {
        super(String.format("Station with id: %s is not availble", id));
    }
}
