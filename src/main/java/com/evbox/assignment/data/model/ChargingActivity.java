package com.evbox.assignment.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Charging activity data model
 * <p>
 * Implements {@code Comparable} interface to be kept sorted in TreeMap.
 */
@Getter
@AllArgsConstructor
public class ChargingActivity implements Comparable<ChargingActivity> {
    LocalDateTime activityTime;
    UUID sessionId;

    @Override
    public int compareTo(ChargingActivity target) {
        return activityTime.compareTo(target.getActivityTime());
    }

}
