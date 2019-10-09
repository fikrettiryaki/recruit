package com.evbox.assignment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evbox.assignment.data.dto.ChargeSessionDto;
import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.exceptions.StationNotAvailableException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChargeSessionManagerImpl implements ChargeSessionManager {

	private final ChargeSessionData chargeSessionData;
	private final StationSessionManager stationSessionManager;
	
	@Override
	public synchronized ChargeSessionDto createSession(String stationId) {
		
		if(!stationSessionManager.isStationAvailable(stationId)) {
			throw new StationNotAvailableException(stationId);
		}
		
		final ChargeSessionDto session = chargeSessionData.addSession(
				ChargeSessionDto.builder()
				.startedAt(LocalDateTime.now())
				.stationId(stationId)
				.status(StatusEnum.IN_PROGRESS)
				.build()
				);
		
		stationSessionManager.registerSessionToStation(stationId, session.getId());
		
		return session;
		
	}

	@Override
	public ChargeSessionDto stopSession(UUID id) {
		
		final ChargeSessionDto sessionDto = chargeSessionData.getSession(id);
		
		ChargeSessionDto updateDto = ChargeSessionDto.builder()
				.id(sessionDto.getId())
				.startedAt(sessionDto.getStartedAt())
				.stoppedAt(LocalDateTime.now())
				.stationId(sessionDto.getStationId())
				.status(StatusEnum.FINISHED)
				.build();

		return chargeSessionData.updateSession(updateDto);
	}

	@Override
	public List<ChargeSessionDto> getAll() {
		return chargeSessionData.getAll();
	}

	@Override
	public SummaryDto getSummary() {
		
		int total = 0;
		int started = 0;
		int finished = 0;
		
		List<ChargeSessionDto> lastMinuteNodes =  chargeSessionData.getLastNodes(LocalDateTime.now().minusMinutes(1));
		
		for( ChargeSessionDto dto :  lastMinuteNodes) {
		
			total++;
			if(dto.getStatus() == StatusEnum.FINISHED) {
				finished++;
			}else {
				started++;
			}
		}
		
		return new SummaryDto(total, started, finished);
		
	}


}
