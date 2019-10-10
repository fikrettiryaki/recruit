package com.evbox.assignment.exceptions;

import java.util.UUID;

/**
 * Exception intended to be used for unmatching {@code ChargingSession) ID
 */
public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(UUID id) {
        super(String.format("Session with id: %s not found in the system", id));
    }
}
