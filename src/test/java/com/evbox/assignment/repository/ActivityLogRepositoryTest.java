package com.evbox.assignment.repository;

import com.evbox.assignment.data.dto.SummaryDto;
import com.evbox.assignment.data.enums.StatusEnum;
import com.evbox.assignment.repository.ActivityLogRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ActivityLogRepositoryTest {

    ActivityLogRepository activityLogRepository;

    @Before
    public void setUp() {
        activityLogRepository = new ActivityLogRepository();
    }

    @Test
    public void log_FINISHED_success() {
        activityLogRepository.log(LocalDateTime.now(), UUID.randomUUID(), StatusEnum.FINISHED);
        SummaryDto summaryDto = activityLogRepository.getSummary(LocalDateTime.MIN);

        assertEquals(1, summaryDto.getStoppedCount());
        assertEquals(0, summaryDto.getStartedCount());
    }

    @Test
    public void log_IN_PROGRESS_success() {
        activityLogRepository.log(LocalDateTime.now(), UUID.randomUUID(), StatusEnum.IN_PROGRESS);
        SummaryDto summaryDto = activityLogRepository.getSummary(LocalDateTime.MIN);

        assertEquals(0, summaryDto.getStoppedCount());
        assertEquals(1, summaryDto.getStartedCount());
    }

    @Test
    public void getSummary_success() {

        LocalDateTime activityTime = LocalDateTime.now();

        activityLogRepository.log(activityTime, UUID.randomUUID(), StatusEnum.IN_PROGRESS);
        activityLogRepository.log(activityTime, UUID.randomUUID(), StatusEnum.FINISHED);
        activityLogRepository.log(activityTime, UUID.randomUUID(), StatusEnum.FINISHED);

        activityLogRepository.log(activityTime.minusYears(1), UUID.randomUUID(), StatusEnum.IN_PROGRESS);
        activityLogRepository.log(activityTime.minusYears(1), UUID.randomUUID(), StatusEnum.FINISHED);

        SummaryDto summaryDto = activityLogRepository.getSummary(activityTime.minusMinutes(1));

        assertEquals(2, summaryDto.getStoppedCount());
        assertEquals(1, summaryDto.getStartedCount());
    }


}
