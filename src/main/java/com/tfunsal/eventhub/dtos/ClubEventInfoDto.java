package com.tfunsal.eventhub.dtos;

import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ClubEventInfoDto {

    private String name;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventCategory eventCategory;
    private EventType eventType;
    private Long participantLimit;
    private List<UserDto> users = new ArrayList<>();

}
