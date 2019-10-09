package com.evbox.assignment.data.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.evbox.assignment.data.enums.StatusEnum;

import lombok.Data;

@Data
public class ChargeSession {
	UUID id;
	String stationId;
	LocalDateTime startedAt;
	LocalDateTime stoppedAt;
	StatusEnum status;
}
