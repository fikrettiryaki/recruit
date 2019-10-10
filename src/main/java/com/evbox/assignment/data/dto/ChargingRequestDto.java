package com.evbox.assignment.data.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ChargingRequestDto {
    final String stationId;

    @JsonCreator
    public ChargingRequestDto(@JsonProperty(value = "stationId", required = true) String stationId) {
        this.stationId = stationId;
    }

}
