package com.tfunsal.eventhub.dtos;

import com.tfunsal.eventhub.models.Event;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ClubDto {

    private String name;
    private String description;
    private String photoUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<Event> events;
}
