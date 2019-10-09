package com.evbox.assignment.service;

import java.util.UUID;

public interface StationSessionManager {
	
	void registerSessionToStation(String stationId, UUID sessionId);
	
	boolean isStationAvailable(String stationId);

}
