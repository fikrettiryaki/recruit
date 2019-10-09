package com.evbox.assignment.exceptions;

public class StationNotAvailableException extends RuntimeException {

	public StationNotAvailableException(String id) {
        super(String.format( "Station with id: %s is not availble" , id));
    }
}
