package com.evbox.assignment.service;

import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.data.model.ChargingActivity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Activity logger to keep track of activities.
 * Start and stop activities are logged seperately to enable log(n) time for retrieving activity size
 * Read and write operations are thread safe using seperate locks for read and write operations.
 */
@Service
public class ActivityLogService {

    final TreeSet<ChargingActivity> timeSortedStartActivity = new TreeSet<>();
    final TreeSet<ChargingActivity> timeSortedStopActivity = new TreeSet<>();

    final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Logs activity to either of treeSets depending on the activity type
     *
     * @param activityTime   LocalDateTime concerning the status change
     * @param sessionId      UUID of ChargingSession for a possible detail reporting operation.
     * @param activityStatus StatusEnum determines the type of logging.
     */
    public void log(final LocalDateTime activityTime, final UUID sessionId, final StatusEnum activityStatus) {
        if (activityStatus == StatusEnum.IN_PROGRESS) {
            lock.writeLock().lock();
            timeSortedStartActivity.add(new ChargingActivity(activityTime, sessionId));
            lock.writeLock().unlock();
        } else {
            lock.writeLock().lock();
            timeSortedStopActivity.add(new ChargingActivity(activityTime, sessionId));
            lock.writeLock().unlock();
        }
    }

    /**
     * Generates activity summary starting from the time provided.
     * <p>
     * SummaryDto.totalCount total number of start and stop activities
     * SummaryDto.startCount total number of start activities
     * SummaryDto.stopCount total number of stop activities
     * <p>
     * A start and stop activity for the same ChargingSession that occured in the given interval
     * are considered seperately to be alligned with the assignment.
     *
     * @param startFrom
     * @return immutable {@code SummaryDto}
     */
    public synchronized SummaryDto getSummary(final LocalDateTime startFrom) {

        final ChargingActivity activityStartFrom = new ChargingActivity(startFrom, null);

        lock.readLock().lock();
        final int startedCount = timeSortedStartActivity.tailSet(activityStartFrom).size();
        final int stoppedCount = timeSortedStopActivity.tailSet(activityStartFrom).size();
        lock.readLock().unlock();

        return new SummaryDto(startedCount + stoppedCount, startedCount, stoppedCount);
    }

}
