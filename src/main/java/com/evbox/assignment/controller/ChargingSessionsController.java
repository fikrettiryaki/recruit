package com.evbox.assignment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evbox.assignment.data.dto.ChargingSessionDto;
import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.service.ChargingSessionManagerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("chargingSessions")
public class ChargingSessionsController {
	
	private final ChargingSessionManagerService chargeSessionManager;
	
	@GetMapping
	public ResponseEntity<List<ChargingSessionDto>> getAll(){	
		return ResponseEntity.ok(chargeSessionManager.getAll());
		
	}
	
	@GetMapping("/summary")
	public ResponseEntity<SummaryDto> getSummary(){	
		return ResponseEntity.ok(chargeSessionManager.getSummary());
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ChargingSessionDto> stopSession(@PathVariable UUID id){	
		return ResponseEntity.ok(chargeSessionManager.stopSession(id));
		
	}
	
	@PostMapping
	public ResponseEntity<ChargingSessionDto> createSession(@RequestParam String stationId){	
		return ResponseEntity.ok(chargeSessionManager.createSession(stationId));
		
	}
	

}
