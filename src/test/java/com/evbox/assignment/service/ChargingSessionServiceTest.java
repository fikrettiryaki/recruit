package com.evbox.assignment.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.exceptions.SessionStateException;
import com.evbox.assignment.exceptions.StationNotAvailableException;
import com.evbox.assignment.repository.ActivityLogRepository;
import com.evbox.assignment.repository.ChargingSessionRepository;
import com.evbox.assignment.repository.StationSessionRepository;

@RunWith(MockitoJUnitRunner.class)
public class ChargingSessionServiceTest {
	
	@Mock
	ChargingSessionRepository chargeSessionRepository;
	
	@Mock
	StationSessionRepository stationSessionRepository;
	
	@Mock
	ActivityLogRepository activityLogRepository;
	
	ChargingSessionService chargingSessionService;
	
	@Before
	public void setup() {
		chargingSessionService = new ChargingSessionService(chargeSessionRepository, stationSessionRepository, activityLogRepository);
		
	}
	
	@Test(expected = StationNotAvailableException.class)
	public void createSession_station_not_available() {
		String stationId = "some station id";
		
		//GIVEN
		when(stationSessionRepository.isStationAvailable(stationId)).thenThrow(new StationNotAvailableException(stationId));
		
		//WHEN
		chargingSessionService.createSession(stationId);
		
		//THEN throws exception
	}
	

	@Test
	public void createSession_success() {
		String stationId = "some station id";
		ChargingSessionDto sessionDto = new ChargingSessionDto(UUID.randomUUID(), stationId, LocalDateTime.now(), null, StatusEnum.IN_PROGRESS);
		
		//GIVEN
		when(stationSessionRepository.isStationAvailable(stationId)).thenReturn(true);
		when(chargeSessionRepository.addSession(stationId)).thenReturn(sessionDto);
		
		//WHEN
		ChargingSessionDto session = chargingSessionService.createSession(stationId);
		
		//THEN
		assertEquals(stationId ,session.getStationId());
		assertEquals(session ,sessionDto);
		verify(chargeSessionRepository, times(1)).addSession(stationId);
		verify(stationSessionRepository, times(1)).registerSessionToStation(stationId, session.getId());
		verify(activityLogRepository, times(1)).log(session.getStartedAt(), session.getId(), session.getStatus());
		
	}
	
	@Test
	public void stopSession_success() {
		String stationId = "some station id";
		ChargingSessionDto sessionDto = new ChargingSessionDto(UUID.randomUUID(), stationId, LocalDateTime.now(), LocalDateTime.now(), StatusEnum.FINISHED);
		
		//GIVEN
		when(chargeSessionRepository.stopSession(sessionDto.getId())).thenReturn(sessionDto);
		
		//WHEN
		ChargingSessionDto session = chargingSessionService.stopSession(sessionDto.getId());
		
		//THEN
		assertEquals(stationId ,session.getStationId());
		assertEquals(session ,sessionDto);
		verify(stationSessionRepository, times(1)).releaseStation(stationId);
		verify(activityLogRepository, times(1)).log(session.getStoppedAt(), session.getId(), session.getStatus());
		
	}
	
	@Test(expected = SessionStateException.class)
	public void stopSession_alreadyStopped() {
		String stationId = "some station id";
		ChargingSessionDto sessionDto = new ChargingSessionDto(UUID.randomUUID(), stationId, LocalDateTime.now(), LocalDateTime.now(), StatusEnum.FINISHED);
		
		//GIVEN
		when(chargeSessionRepository.stopSession(sessionDto.getId())).thenThrow(new SessionStateException(sessionDto.getId()));
		
		//WHEN
		ChargingSessionDto session = chargingSessionService.stopSession(sessionDto.getId());
		
		//THEN
		verify(stationSessionRepository, times(0)).releaseStation(stationId);
		verify(activityLogRepository, times(0)).log(session.getStoppedAt(), session.getId(), session.getStatus());
		
	}
	
	@Test
	public void getAll_success() {
		String stationId = "some station id";
		ChargingSessionDto sessionDto = new ChargingSessionDto(UUID.randomUUID(), stationId, LocalDateTime.now(), LocalDateTime.now(), StatusEnum.FINISHED);
		
		//GIVEN
		when(chargeSessionRepository.getAll()).thenReturn(Arrays.asList(sessionDto));
		
		//WHEN
		List<ChargingSessionDto> sessions = chargingSessionService.getAll();
		
		//THEN
		assertEquals(stationId ,sessions.get(0).getStationId());
		assertEquals(sessionDto ,sessions.get(0));
		
	}
	
}
