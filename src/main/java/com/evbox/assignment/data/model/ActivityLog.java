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
public class ActivityLog implements Comparable<ActivityLog> {
    LocalDateTime activityTime;
    UUID sessionId;

    @Override
    public int compareTo(ActivityLog target) {
        if(activityTime.equals(target.activityTime)){
            //Check further if those activities are really identical
            return sessionId.compareTo(target.sessionId);
        }
        return activityTime.compareTo(target.getActivityTime());
    }

}
