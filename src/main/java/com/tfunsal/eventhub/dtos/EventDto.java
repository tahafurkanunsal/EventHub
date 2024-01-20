package com.tfunsal.eventhub.dtos;

import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {

    private String name;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventCategory eventCategory;
    private EventType eventType;
    private Long clubId;
    private String clubName;

}
