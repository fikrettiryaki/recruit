package com.evbox.assignment.service;

import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.data.model.ChargingSession;
import com.evbox.assignment.exceptions.SessionNotFoundException;
import com.evbox.assignment.exceptions.SessionStateException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A Concurrent data manager for ChargingSession data objects
 * Provides add, stop and get operations.
 * Data is stored internally in a HashMap. All access to HashMap and multi thread vulnerable sections
 * of code are synchronized wia internal lock therefore a concurrent Map implementaion is redundant.
 */
@Service
public class ChargingSessionDataService {

    final private Map<UUID, ChargingSession> sessionMap = new HashMap<>();
    final private Object lock = new Object();

    /**
     * Updates the ChargingSession state to FINISHED
     *
     * @param sessionId session identifier
     * @return immutable {@code ChargingSessionDto}
     * @throws SessionNotFoundException when no matching session with provided id is found
     * @throws SessionStateException    when session in the system has already state with FINISHED
     */
    public ChargingSessionDto stopSession(final UUID sessionId) {

        synchronized (lock) {
            if (!sessionMap.containsKey(sessionId)) {
                throw new SessionNotFoundException(sessionId);
            }

            final ChargingSession session = sessionMap.get(sessionId);
            if (session.getStatus() == StatusEnum.FINISHED) {
                throw new SessionStateException(sessionId);
            }
            session.setStatus(StatusEnum.FINISHED);
            session.setStoppedAt(LocalDateTime.now());
            return ChargingSessionDto.of(session);
        }


    }

    /**
     * Creates a {@code ChargingSession} with provided station ID
     *
     * @param stationId String as station ID
     * @return immutable {@code ChargingSessionDto}
     */
    public ChargingSessionDto addSession(final String stationId) {

        ChargingSession session = new ChargingSession();
        session.setId(UUID.randomUUID());
        session.setStartedAt(LocalDateTime.now());
        session.setStationId(stationId);
        session.setStatus(StatusEnum.IN_PROGRESS);

        synchronized (lock) {
            sessionMap.put(session.getId(), session);
        }

        return ChargingSessionDto.of(session);
    }

    /**
     * Returns all hosted {@code ChargingSession} in the system
     *
     * @return list of immutable {@code ChargingSessionDto}
     */
    public List<ChargingSessionDto> getAll() {
        synchronized (lock) {
            return sessionMap.values().stream()
                    .map(ChargingSessionDto::of)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    /**
     * Returns matching {@code ChargingSession} in the system
     *
     * @param id as session Id
     * @return immutable {@code ChargingSessionDto}
     */
    public ChargingSessionDto getSession(UUID id) {

        synchronized (lock) {
            if (!sessionMap.containsKey(id)) {
                throw new SessionNotFoundException(id);
            }
            return ChargingSessionDto.of(sessionMap.get(id));
        }
    }

}
