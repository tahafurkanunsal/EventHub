package com.tfunsal.eventhub.services.impl;

import com.tfunsal.eventhub.dtos.EventDto;
import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;
import com.tfunsal.eventhub.models.Club;
import com.tfunsal.eventhub.models.Event;
import com.tfunsal.eventhub.repository.ClubRepository;
import com.tfunsal.eventhub.repository.EventRepository;
import com.tfunsal.eventhub.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final ClubRepository clubRepository;

    @Override
    public List<EventDto> getAllEvent() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(Event::getDto).collect(Collectors.toList());
    }

    @Override
    public EventDto getEventById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        return event.getDto();
    }

    @Override
    public List<EventDto> getEventsByName(String eventName) {
        List<Event> events = eventRepository.findEventsByName(eventName);
        return events.stream().map(Event::getDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getEventsByEventCategory(EventCategory eventCategory) {
        List<Event> events = eventRepository.findEventsByEventCategory(eventCategory);
        return events.stream().map(Event::getDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getEventsByEventType(EventType eventType) {
        List<Event> events = eventRepository.findEventsByEventType(eventType);
        return events.stream().map(Event::getDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getEventsByLocation(String location) {
        List<Event> events = eventRepository.findEventsByLocation(location);
        return events.stream().map(Event::getDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getEventsByClub(Long clubId) {
        List<Event> events = eventRepository.findEventsByClubId(clubId);
        return events.stream().map(Event::getDto).collect(Collectors.toList());
    }

    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = new Event();

        event.setName(eventDto.getName());
        event.setLocation(eventDto.getLocation());
        event.setStartTime(eventDto.getStartTime());
        event.setEndTime(eventDto.getEndTime());
        event.setEventCategory(eventDto.getEventCategory());
        event.setEventType(eventDto.getEventType());

        Optional<Club> club = clubRepository.findById(eventDto.getClubId());
        if (club.isPresent()) {
            event.setClub(club.get());
        }
        return eventRepository.save(event).getDto();
    }

    @Override
    public EventDto updateEvent(Long eventId, EventDto eventDto) {

        Event existingEvent = eventRepository.findById(eventId).get();

        existingEvent.setId(eventId);
        existingEvent.setName(eventDto.getName());
        existingEvent.setLocation(eventDto.getLocation());
        existingEvent.setEventCategory(eventDto.getEventCategory());
        existingEvent.setEventType(eventDto.getEventType());
        existingEvent.setStartTime(eventDto.getStartTime());
        existingEvent.setEndTime(eventDto.getEndTime());

        Optional<Club> club = clubRepository.findById(eventDto.getClubId());
        if (club.isPresent()){
            existingEvent.setClub(club.get());
        }
        return eventRepository.save(existingEvent).getDto();
    }

    @Override
    public boolean deleteEvent(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()){
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }
}
