package com.evbox.assignment.repository;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Station Session mapping repository to host mapped station session information
 * <p>
 * In ideal case the available stations should already be provided.
 * For simplicity of the task, unknown station Ids are treated as available.
 */
@Service
public class StationSessionRepository {


    private final Map<String, UUID> stationSessionMap = new HashMap<>();

    /**
     * Registers a Session to a station
     *
     * @param stationId String as station ID
     * @param sessionId UUID as session ID
     */
    public void registerSessionToStation(String stationId, UUID sessionId) {
        stationSessionMap.put(stationId, sessionId);
    }

    /**
     * Checks if there is a session registered to a station.
     * <p>
     * @return boolean true if station with @stationId is available. 
     */
    public boolean isStationAvailable(String stationId) {
        return !stationSessionMap.containsKey(stationId);
    }
    
    /**
     * Removes the registration information.
     * 
     * @param stationId
     */
    public void releaseStation(String stationId) {
    	stationSessionMap.remove(stationId);
    	
    }

}
