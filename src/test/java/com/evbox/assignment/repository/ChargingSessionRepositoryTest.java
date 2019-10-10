package com.evbox.assignment.repository;

import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.exceptions.SessionNotFoundException;
import com.evbox.assignment.exceptions.SessionStateException;
import com.evbox.assignment.repository.ChargingSessionRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ChargingSessionRepositoryTest {
    ChargingSessionRepository chargingSessionRepository;

    @Before
    public void setUp(){
        chargingSessionRepository = new ChargingSessionRepository();
    }

    @Test
    public void addSession_success(){
        //GIVEN
        final String stationId = "some-station-id";

        //WHEN
        ChargingSessionDto sessionDto = chargingSessionRepository.addSession(stationId);

        //THEN
        assertEquals(StatusEnum.IN_PROGRESS, sessionDto.getStatus());
        assertEquals(stationId, sessionDto.getStationId());

    }

    @Test
    public void stopSession_success(){
        //GIVEN
        ChargingSessionDto sessionDto = chargingSessionRepository.addSession("some-session");

        //WHEN
        ChargingSessionDto responseDto = chargingSessionRepository.stopSession(sessionDto.getId());

        //THEN
        assertEquals(StatusEnum.FINISHED, responseDto.getStatus());
    }

    @Test(expected = SessionNotFoundException.class)
    public void stopSession_notExists_throwsException(){
        //GIVEN
        //WHEN
        chargingSessionRepository.stopSession(UUID.randomUUID());
        //THEN Throws exception
    }

    @Test(expected = SessionStateException.class)
    public void stopSession_alreadyStopped_throwsException(){
        //GIVEN
        ChargingSessionDto sessionDto = chargingSessionRepository.addSession("some-session");
        ChargingSessionDto responseDto = chargingSessionRepository.stopSession(sessionDto.getId());

        //WHEN
        chargingSessionRepository.stopSession(sessionDto.getId());

        //THEN Throws exception
    }

    @Test
    public void getAll_success(){
        //GIVEN
        final String stationId1 = "some-station-id1";
        final String stationId2 = "some-station-id2";
        ChargingSessionDto sessionDto = chargingSessionRepository.addSession("some-session");
        ChargingSessionDto sessionDto2 = chargingSessionRepository.addSession("some-session");

        //WHEN
        ChargingSessionDto responseDto = chargingSessionRepository.stopSession(sessionDto.getId());

        //THEN
        assertEquals(StatusEnum.FINISHED, responseDto.getStatus());
    }



}
