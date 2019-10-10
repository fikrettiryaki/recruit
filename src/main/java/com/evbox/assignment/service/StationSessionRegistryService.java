package com.evbox.assignment.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.enums.StatusEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StationSessionRegistryService {
	
	private final ChargingSessionDataService chargeSessionData;
	
	private final Map<String, UUID> stationSessionMap = new HashMap<>();

	public void registerSessionToStation(String stationId, UUID sessionId) {
		stationSessionMap.put(stationId, sessionId);
		
	}

	/**
	 * Checks if there is a station information registered before. 
	 * If not, the queried station is assumed to be available
	 * If exists, the belonging chargesession status is checked
	 * 
	 * For simplicity, an unknown station id is treated to be an available one.
	 */
	public boolean isStationAvailable(String stationId) {
		if(!stationSessionMap.containsKey(stationId)) {
			return true;
		}
		
		final ChargingSessionDto session = chargeSessionData.getSession(stationSessionMap.get(stationId));
		
        return session.getStatus() == StatusEnum.FINISHED;
	}

}