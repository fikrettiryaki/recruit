package com.evbox.assignment.service;

import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.exceptions.StationNotAvailableException;
import com.evbox.assignment.repository.ActivityLogRepository;
import com.evbox.assignment.repository.ChargingSessionRepository;
import com.evbox.assignment.repository.StationSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Charge session management service
 * Responsible for:
 * start, stop, query of ChargingSessions
 * Logging and reporting Activities
 * Register sessions to stations to avoid multiple active sessions on a station.
 */
@Service
@RequiredArgsConstructor
public class ChargingSessionService {

    private final ChargingSessionRepository chargeSessionRepository;
    private final StationSessionRepository stationSessionRepository;
    private final ActivityLogRepository activityLogRepository;

    /**
     * Creates a session for the station ID provided
     * <p>
     * (1) Checks station availability
     * (2) Adds new ChargingSession
     * (3) Registers charging session to station
     * (4) Logs activity
     * <p>
     * <p>
     * This method is synchronized:
     * StationSessionRegistry state should not be changed externally during the execution of the
     * method. StationSessionRegistry is only modified by this function, therefore synchronizing it
     * ensure the integrity of StationSessionRegistry state.
     *
     * @param stationId as the id of the station {@code String}
     * @return generated {@code ChargingSessionDto}
     */
    public synchronized ChargingSessionDto createSession(final String stationId) {

        if (!stationSessionRepository.isStationAvailable(stationId)) {
            throw new StationNotAvailableException(stationId);
        }

        final ChargingSessionDto session = chargeSessionRepository.addSession(stationId);
        stationSessionRepository.registerSessionToStation(stationId, session.getId());
        activityLogRepository.log(session.getStartedAt(), session.getId(), StatusEnum.IN_PROGRESS);

        return session;

    }

    /**
     * Stops the active charging session
     * 
     * 1-Session is finalized
     * 2-station is released
     * 3-session stop activity is logged
     *
     * @param id as session identifier to be stopped
     * @return updated {@code ChargingSessionDto}
     */
    public synchronized ChargingSessionDto stopSession(final UUID id) {

        final ChargingSessionDto session = chargeSessionRepository.stopSession(id);
        stationSessionRepository.releaseStation(session.getStationId());
        activityLogRepository.log(session.getStoppedAt(), session.getId(), StatusEnum.FINISHED);
        
        return session;
    }

    /**
     * Returns the list of ChargingSession in the system
     *
     * @return list of {@code ChargingSessionDto}
     */
    public List<ChargingSessionDto> getAll() {

        return chargeSessionRepository.getAll();
    }

    /**
     * Returns the list of ChargingSession in the system
     *
     * @return list of {@code ChargingSessionDto}
     */
    public synchronized SummaryDto getSummary() {

        return activityLogRepository.getSummary(LocalDateTime.now().minusMinutes(1));
    }

}
