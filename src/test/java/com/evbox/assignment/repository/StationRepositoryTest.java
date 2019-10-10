package com.evbox.assignment.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class StationRepositoryTest {
	
	StationSessionRepository stationSessionRepository;
	
	@Before
	public void setup() {
		stationSessionRepository = new StationSessionRepository();
	}
	
	@Test
	public void isStationAvailable_true() {
		String stationId = "some station id";
		
		assertTrue(stationSessionRepository.isStationAvailable(stationId));
		
	}
	
	@Test
	public void isStationAvailable_false() {
		String stationId = "some station id";
		
		//GIVEN
		stationSessionRepository.registerSessionToStation(stationId, UUID.randomUUID());
		
		//WHEN 
		//THEN
		assertFalse(stationSessionRepository.isStationAvailable(stationId));
		
	}
	
	@Test
	public void releaseStation_success() {
		String stationId = "some station id";
		
		//GIVEN
		stationSessionRepository.registerSessionToStation(stationId, UUID.randomUUID());
		
		//WHEN 
		stationSessionRepository.releaseStation(stationId);
		
		//THEN
		assertTrue(stationSessionRepository.isStationAvailable(stationId));
		
	}

}
