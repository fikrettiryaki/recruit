package com.evbox.assignment.data.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.data.model.ChargingSession;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ChargingSessionDto {
	UUID id;
	String stationId;
	LocalDateTime startedAt;
	LocalDateTime stoppedAt;
	StatusEnum status;
	
	public static ChargingSessionDto of(final ChargingSession session) {
		return new ChargingSessionDto(
				session.getId(), 
				session.getStationId(), 
				session.getStartedAt(), 
				session.getStoppedAt(), 
				session.getStatus());
	}
}
