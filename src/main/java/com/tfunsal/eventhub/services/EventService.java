package com.tfunsal.eventhub.services;

import com.tfunsal.eventhub.dtos.EventDto;
import com.tfunsal.eventhub.dtos.EventUpdateDto;
import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;

import java.util.List;

public interface EventService {

    List<EventDto> getAllEvent();

    EventDto getEventById(Long id);

    List<EventDto> getEventsByName(String eventName);

    List<EventDto> getEventsByEventCategory(EventCategory eventCategory);

    List<EventDto> getEventsByEventType(EventType eventType);

    List<EventDto> getEventsByLocation(String Location);

    List<EventDto> getEventsByClub(Long clubId);

    EventDto createEvent(EventDto eventDto);

    EventDto updateEvent(Long eventId, EventUpdateDto eventUpdateDto);

    boolean deleteEvent(Long eventId);

    EventDto joinTheEvent(Long eventId , Long userId);

    EventDto leaveTheEvent(Long eventId , Long userId);

}