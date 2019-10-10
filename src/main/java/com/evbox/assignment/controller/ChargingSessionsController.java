package com.evbox.assignment.controller;

import com.evbox.assignment.data.dto.ChargingRequestDto;
import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.service.ChargingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("chargingSessions")
public class ChargingSessionsController {

    private final ChargingSessionService chargeSessionManager;

    /**
     * Rest endpoint to get the list of charging sessions
     * Method: GET
     *
     * @return {@link <List<ChargingSessionDto>}
     */
    @GetMapping
    public ResponseEntity<List<ChargingSessionDto>> getAll() {
        return ResponseEntity.ok(chargeSessionManager.getAll());

    }

    /**
     * Returns the summary of recent activities
     *
     * @return {@link SummaryDto}
     */
    @GetMapping("/summary")
    public ResponseEntity<SummaryDto> getSummary() {
        return ResponseEntity.ok(chargeSessionManager.getSummary());

    }

    /**
     * Stops the session with provided id
     *
     * @param id {@link UUID}
     * @return {@link ChargingSessionDto}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ChargingSessionDto> stopSession(@PathVariable UUID id) {
        return ResponseEntity.ok(chargeSessionManager.stopSession(id));

    }

    /**
     * Initiates a charging session for the station ID probided
     *
     * @param requestDto encapsulates stationId
     * @return generated {@link ChargingSessionDto}
     */
    @PostMapping
    public ResponseEntity<ChargingSessionDto> createSession(@RequestBody ChargingRequestDto requestDto) {
        return ResponseEntity.ok(chargeSessionManager.createSession(requestDto.getStationId()));

    }


}
