package com.evbox.assignment.service;

import java.util.List;
import java.util.UUID;

import com.evbox.assignment.data.dto.ChargeSessionDto;
import com.evbox.assignment.data.dto.SummaryDto;


public interface ChargeSessionManager {
	
	ChargeSessionDto createSession(String stationId);
	ChargeSessionDto stopSession(UUID id);
	List<ChargeSessionDto> getAll();
	SummaryDto getSummary();

}
