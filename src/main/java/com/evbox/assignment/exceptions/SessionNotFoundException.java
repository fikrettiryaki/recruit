package com.evbox.assignment.exceptions;

import java.util.UUID;

public class SessionNotFoundException extends RuntimeException {


	public SessionNotFoundException(UUID id) {
        super(String.format( "Session with id: %s not found in the system" , id));
    }
}
