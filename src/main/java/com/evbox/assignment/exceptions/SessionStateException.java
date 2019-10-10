package com.evbox.assignment.exceptions;

import java.util.UUID;

/**
 * Exception for {@code ChargingSession) state update problems
 */
public class SessionStateException extends RuntimeException {

    public SessionStateException(UUID id) {
        super(String.format("Session with id: %s state is not available for the operation", id));
    }
}
