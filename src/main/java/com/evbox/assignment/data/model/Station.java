package com.evbox.assignment.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Station {
    private String stationId;
    private UUID sessionId;
}
