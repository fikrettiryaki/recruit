package com.evbox.assignment.service;

import java.time.LocalDateTime;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.data.model.ChargingActivity;

@Service
public class ActivityLogService {
	
	final TreeSet<ChargingActivity> timeSortedStartActivity = new TreeSet<>();
	final TreeSet<ChargingActivity> timeSortedStopActivity = new TreeSet<>();

	public void log(final LocalDateTime activityTime, final UUID sessionId, final StatusEnum activityStatus) {
		if(activityStatus == StatusEnum.IN_PROGRESS) {
			timeSortedStartActivity.add(new ChargingActivity(activityTime, sessionId));
		}else {
			timeSortedStopActivity.add(new ChargingActivity(activityTime, sessionId));
		}
	}
	
	public synchronized SummaryDto getSummary(final LocalDateTime startFrom) {
		
		final ChargingActivity activityStartFrom = new ChargingActivity(startFrom, null);
		
		final int startedCount = timeSortedStartActivity.tailSet(activityStartFrom).size();
		final int stoppedCount = timeSortedStopActivity.tailSet(activityStartFrom).size();
		
		return new SummaryDto(startedCount + stoppedCount, startedCount, stoppedCount);
	}

}
