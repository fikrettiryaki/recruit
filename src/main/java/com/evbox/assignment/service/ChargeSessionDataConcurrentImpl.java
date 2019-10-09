package com.evbox.assignment.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.evbox.assignment.data.Node;
import com.evbox.assignment.data.dto.ChargeSessionDto;
import com.evbox.assignment.data.model.ChargeSession;
import com.evbox.assignment.exceptions.SessionNotFoundException;

@Component
public class ChargeSessionDataConcurrentImpl implements ChargeSessionData{


	//We do not need concurrent hash map as access points are already synchronized. 
	//Adding extra synchronization only slows down the operation
	Map<UUID, ChargeSession> sessionMap = new HashMap<>();

	//Extra data structure to keep order of sessions in the LIFO order
	Node sessionList = null;

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

		Node node = new Node(session);

		if(sessionList != null) {
			node.setNext(sessionList);
		}

		sessionList = node;

		return ChargeSessionDto.of(session);
	}

	@Override
	public synchronized List<ChargeSessionDto> getAll() {
		List<ChargeSessionDto> sessionDtos = new ArrayList<>();

		Node node = sessionList;

		while(node != null ) {
			sessionDtos.add(ChargeSessionDto.of(node.getValue()));
			node = node.getNext();
		}
		return sessionDtos;
	}

	@Override
	public synchronized List<ChargeSessionDto> getLastNodes(LocalDateTime startFrom) {

		List<ChargeSessionDto> lastNodes = new ArrayList<>();
		Node node = sessionList;

		while(node != null && node.getValue().getStartedAt().isAfter( startFrom)) {
			lastNodes.add(ChargeSessionDto.of(node.getValue()));
			node = node.getNext();
		}
		return lastNodes;

	}

	@Override
	public synchronized ChargeSessionDto getSession(UUID id) {
		if(!sessionMap.containsKey(id)) {
			throw new SessionNotFoundException(id);
		}
		return ChargeSessionDto.of(sessionMap.get(id));

	}

}
