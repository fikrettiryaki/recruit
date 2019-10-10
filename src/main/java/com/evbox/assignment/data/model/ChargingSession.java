package com.evbox.assignment.data.model;

import com.evbox.assignment.data.enums.StatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Charging session data model
 */
@Data
public class ChargingSession {
    UUID id;
    String stationId;
    LocalDateTime startedAt;
    LocalDateTime stoppedAt;
    StatusEnum status;
}
