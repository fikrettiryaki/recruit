package com.evbox.assignment.service;

import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Station registry service to match stations to sessions
 * <p>
 * In ideal case the available stations should already be provided.
 * For simplicity of the task, unknown station Ids are treated as available.
 */
@Service
@RequiredArgsConstructor
public class StationRegistryService {

    private final ChargingSessionDataService chargeSessionData;

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
     * Checks if there is a station information registered before.
     * If not, the queried station is assumed to be available
     * If exists, the belonging chargesession status is checked
     * <p>
     * For simplicity, an unknown station id is treated to be an available one.
     */
    public boolean isStationAvailable(String stationId) {
        if (!stationSessionMap.containsKey(stationId)) {
            return true;
        }

        final ChargingSessionDto session = chargeSessionData.getSession(stationSessionMap.get(stationId));
        return session.getStatus() == StatusEnum.FINISHED;
    }

}
