package com.evbox.assignment.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.evbox.assignment.exceptions.StationNotAvailableException;

import static org.mockito.Mockito.when;

public class ChargeSessionManagerImplTest {
	
	ChargeSessionManagerImpl fixture;
	
	@Mock
	ChargeSessionData chargeSessionData;
	
	@Mock
	StationSessionManager stationSessionManager;
	
	@Before
	public void setup() {
		fixture = new ChargeSessionManagerImpl(chargeSessionData, stationSessionManager);
	}
	
	@Test(expected = StationNotAvailableException.class)
	public void createSession_unavailableStation_throwsException() {
		final String stationId = "some station id";
		
		when(stationSessionManager.isStationAvailable(stationId)).thenThrow(new StationNotAvailableException(stationId));
	fixture.createSession(stationId);
	
	}
	
	

}
