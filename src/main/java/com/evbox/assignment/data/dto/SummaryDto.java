package com.evbox.assignment.data.dto;

import lombok.Value;

/**
 * Immutable activity summary model
 */
@Value
public class SummaryDto {
    int totalCount;
    int startedCount;
    int stoppedCount;
}
