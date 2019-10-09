package com.evbox.assignment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evbox.assignment.data.dto.ChargeSessionDto;
import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.service.ChargeSessionManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("chargingSessions")
public class ChargingSessionsController {
	
	private final ChargeSessionManager chargeSessionManager;
	
	@GetMapping
	public List<ChargeSessionDto> getAll(){	
		return chargeSessionManager.getAll();
		
	}
	
	@GetMapping("summary")
	public SummaryDto getSummary(){	
		return chargeSessionManager.getSummary();
		
	}
	
	@PutMapping("{id}")
	public ChargeSessionDto stopSession(@PathVariable UUID id){	
		return chargeSessionManager.stopSession(id);
		
	}
	
	@PostMapping
	public ChargeSessionDto createSession(@RequestParam String stationId){	
		return chargeSessionManager.createSession(stationId);
		
	}
	

}
