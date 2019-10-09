package com.evbox.assignment.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.evbox.assignment.data.dto.ChargeSessionDto;
import com.evbox.assignment.data.model.ChargeSession;
import com.evbox.assignment.exceptions.SessionNotFoundException;

@Component
public class ChargeSessionDataConcurrentImpl implements ChargeSessionData{


	final TreeMap<LocalDateTime, ChargeSession> timeSortedMap = new TreeMap<>();
	final Map<UUID, ChargeSession> sessionMap = new HashMap<>();

	@Override
	public synchronized ChargeSessionDto updateSession(final ChargeSessionDto sessionDto) {
		

		if(!sessionMap.containsKey(sessionDto.getId())) {
			throw new SessionNotFoundException(sessionDto.getId());
		}
		final ChargeSession session =  sessionMap.get(sessionDto.getId());	
		session.setStartedAt(sessionDto.getStartedAt());
		session.setStationId(sessionDto.getStationId());
		session.setStatus(sessionDto.getStatus());
		session.setStoppedAt(sessionDto.getStoppedAt());
		return ChargeSessionDto.of(session);

	}

	@Override
	public synchronized ChargeSessionDto addSession(final ChargeSessionDto sessionDto) {
		ChargeSession session = new ChargeSession();
		session.setId(UUID.randomUUID());
		session.setStartedAt(sessionDto.getStartedAt());
		session.setStationId(sessionDto.getStationId());
		session.setStatus(sessionDto.getStatus());
		session.setStoppedAt(sessionDto.getStoppedAt());


		sessionMap.put(session.getId(),session);
		timeSortedMap.put(session.getStartedAt(), session);

		return ChargeSessionDto.of(session);
	}

	@Override
	public synchronized List<ChargeSessionDto> getAll() {
		
		List<ChargeSessionDto> sessionDtos = new ArrayList<>();

		for(ChargeSession session : sessionMap.values()) {
			sessionDtos.add(ChargeSessionDto.of(session));
		}
		return sessionDtos;
	}

	@Override
	public synchronized List<ChargeSessionDto> getLastNodes(LocalDateTime startFrom) {

		Collection<ChargeSession> lastSessions = timeSortedMap.tailMap(startFrom).values();

		List<ChargeSessionDto> sessionDtos = new ArrayList<>();

		for(ChargeSession session : lastSessions) {
			sessionDtos.add(ChargeSessionDto.of(session));
		}
		return sessionDtos;

	}

	@Override
	public synchronized ChargeSessionDto getSession(UUID id) {
		
		if(!sessionMap.containsKey(id)) {
			throw new SessionNotFoundException(id);
		}
		return ChargeSessionDto.of(sessionMap.get(id));

	}

}
