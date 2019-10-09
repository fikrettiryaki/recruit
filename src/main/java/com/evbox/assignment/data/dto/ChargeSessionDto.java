package com.evbox.assignment.data.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.data.model.ChargeSession;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ChargeSessionDto {
	UUID id;
	String stationId;
	LocalDateTime startedAt;
	LocalDateTime stoppedAt;
	StatusEnum status;
	
	public static ChargeSessionDto of(final ChargeSession session) {
		return new ChargeSessionDto(
				session.getId(), 
				session.getStationId(), 
				session.getStartedAt(), 
				session.getStoppedAt(), 
				session.getStatus());
	}
}
