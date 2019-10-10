package com.evbox.assignment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.exceptions.StationNotAvailableException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChargingSessionManagerService {

	private final ChargingSessionDataService chargeSessionData;
	private final StationSessionRegistryService stationSessionRegistry;
	private final ActivityLogService activityLoger;
	
	public synchronized ChargingSessionDto createSession(String stationId) {
		
		if(!stationSessionRegistry.isStationAvailable(stationId)) {
			throw new StationNotAvailableException(stationId);
		}
		
		final ChargingSessionDto session = chargeSessionData.addSession(stationId);
		
		stationSessionRegistry.registerSessionToStation(stationId, session.getId());
		
		activityLoger.log(session.getStartedAt(), session.getId(), StatusEnum.IN_PROGRESS);
		
		return session;
		
	}

	public ChargingSessionDto stopSession(UUID id) {
		
		final ChargingSessionDto sessionDto = chargeSessionData.getSession(id);
		
		ChargingSessionDto updateDto = ChargingSessionDto.builder()
				.id(sessionDto.getId())
				.startedAt(sessionDto.getStartedAt())
				.stoppedAt(LocalDateTime.now())
				.stationId(sessionDto.getStationId())
				.status(StatusEnum.FINISHED)
				.build();

		ChargingSessionDto session = chargeSessionData.stopSession(id);
		
		activityLoger.log(session.getStoppedAt(), session.getId(), StatusEnum.FINISHED);
		
		return session;
	}

	public List<ChargingSessionDto> getAll() {
		return chargeSessionData.getAll();
	}

	public SummaryDto getSummary() {
		
		return  activityLoger.getSummary(LocalDateTime.now().minusMinutes(1));
		
	}


}
