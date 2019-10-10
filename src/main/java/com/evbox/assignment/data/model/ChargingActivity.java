package com.evbox.assignment.data.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Value;

@Value
public class ChargingActivity implements Comparable<ChargingActivity> {
	LocalDateTime activityTime;
	UUID sessionId;
	
	@Override
	public int compareTo(ChargingActivity target) {
		return activityTime.compareTo(target.getActivityTime());
	}

}
