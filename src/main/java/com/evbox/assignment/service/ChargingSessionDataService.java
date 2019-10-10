package com.evbox.assignment.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.data.model.ChargingActivity;
import com.evbox.assignment.data.model.ChargingSession;
import com.evbox.assignment.exceptions.SessionNotFoundException;

@Component
public class ChargingSessionDataService {

	final Map<UUID, ChargingSession> sessionMap = new HashMap<>();

	public synchronized ChargingSessionDto stopSession(final UUID sessionId) {
		
		if(!sessionMap.containsKey(sessionId)) {
			throw new SessionNotFoundException(sessionId);
		}
		
		final ChargingSession session =  sessionMap.get(sessionId);	

		session.setStatus(StatusEnum.FINISHED);
		session.setStoppedAt(LocalDateTime.now());
		
		
		return ChargingSessionDto.of(session);

	}

	public synchronized ChargingSessionDto addSession(final String stationId) {
		
		ChargingSession session = new ChargingSession();
		session.setId(UUID.randomUUID());
		session.setStartedAt(LocalDateTime.now());
		session.setStationId(stationId);
		session.setStatus(StatusEnum.IN_PROGRESS);
		sessionMap.put(session.getId(),session);

		return ChargingSessionDto.of(session);
	}

	public synchronized List<ChargingSessionDto> getAll() {
		
		List<ChargingSessionDto> sessionDtos = new ArrayList<>();

		for(ChargingSession session : sessionMap.values()) {
			sessionDtos.add(ChargingSessionDto.of(session));
		}
		
		return sessionDtos;
	}
	
	public synchronized ChargingSessionDto getSession(UUID id) {
		
		if(!sessionMap.containsKey(id)) {
			throw new SessionNotFoundException(id);
		}
		
		return ChargingSessionDto.of(sessionMap.get(id));

	}

}
