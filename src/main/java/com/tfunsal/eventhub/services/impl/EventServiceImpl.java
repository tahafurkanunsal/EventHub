package com.tfunsal.eventhub.services.impl;

import com.tfunsal.eventhub.dtos.EventDto;
import com.tfunsal.eventhub.dtos.EventUpdateDto;
import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;
import com.tfunsal.eventhub.exception.ClubNotFoundException;
import com.tfunsal.eventhub.exception.EventNotFoundException;
import com.tfunsal.eventhub.exception.UserAlreadyParticipatingException;
import com.tfunsal.eventhub.exception.UserNotFoundException;
import com.tfunsal.eventhub.models.Club;
import com.tfunsal.eventhub.models.Event;
import com.tfunsal.eventhub.models.User;
import com.tfunsal.eventhub.repository.ClubRepository;
import com.tfunsal.eventhub.repository.EventRepository;
import com.tfunsal.eventhub.repository.UserRepository;
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

    private final UserRepository userRepository;

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
    public EventDto getEventByEventIdAndClubId(Long eventId, Long clubId) {
        Event event = eventRepository.findEventByIdAndClubId(eventId , clubId);
        return event.getDto();
    }

    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = new Event();

        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setLocation(eventDto.getLocation());
        event.setStartTime(eventDto.getStartTime());
        event.setEndTime(eventDto.getEndTime());
        event.setEventCategory(eventDto.getEventCategory());
        event.setEventType(eventDto.getEventType());
        event.setParticipantLimit(eventDto.getParticipantLimit());

        Optional<Club> clubOptional = clubRepository.findById(eventDto.getClubId());

        if (clubOptional.isPresent()) {
            Club club = clubOptional.get();
            event.setClub(club);

            Event savedEvent = eventRepository.save(event);

            EventDto newEventDto = new EventDto();
            newEventDto.setId(savedEvent.getId());
            newEventDto.setName(savedEvent.getName());
            newEventDto.setLocation(savedEvent.getLocation());
            newEventDto.setStartTime(savedEvent.getStartTime());
            newEventDto.setEndTime(savedEvent.getEndTime());
            newEventDto.setEventCategory(savedEvent.getEventCategory());
            newEventDto.setEventType(savedEvent.getEventType());
            newEventDto.setParticipantLimit(savedEvent.getParticipantLimit());
            newEventDto.setClubId(savedEvent.getClub().getId());
            newEventDto.setClubName(savedEvent.getClub().getName());

            return newEventDto;
        } else {
            throw new ClubNotFoundException("Club with ID " + eventDto.getClubId() + " not found");
        }
    }

    @Override
    public EventDto updateEvent(Long eventId, EventUpdateDto eventUpdateDto) {

        Event existingEvent = eventRepository.findById(eventId).get();

        existingEvent.setId(eventId);
        existingEvent.setName(eventUpdateDto.getName());
        existingEvent.setLocation(eventUpdateDto.getLocation());
        existingEvent.setEventCategory(eventUpdateDto.getEventCategory());
        existingEvent.setEventType(eventUpdateDto.getEventType());
        existingEvent.setStartTime(eventUpdateDto.getStartTime());
        existingEvent.setEndTime(eventUpdateDto.getEndTime());
        existingEvent.setParticipantLimit(eventUpdateDto.getParticipantLimit());

        return eventRepository.save(existingEvent).getDto();
    }

    @Override
    public boolean deleteEvent(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }

    @Override
    public EventDto joinTheEvent(Long eventId, Long userId) {

        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            User existingUser = userRepository.findById(userId).orElseThrow();

            Event existingEvent = optionalEvent.get();

            if (!existingEvent.getUsers().contains(existingUser)) {
                existingEvent.getUsers().add(existingUser);
                existingEvent.setParticipantLimit(existingEvent.getParticipantLimit() - 1);

                eventRepository.save(existingEvent);

                return existingEvent.getDto();
            } else {
                throw new UserAlreadyParticipatingException("User is already participating in the event with id: " + userId);
            }
        } else {
            throw new EventNotFoundException("Event not found with id :" + eventId);
        }
    }

    @Override
    public EventDto leaveTheEvent(Long eventId, Long userId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            Event existingEvent = optionalEvent.get();

            Optional<User> optionalUser = existingEvent.getUsers()
                    .stream()
                    .filter(user -> user.getId().equals(userId))
                    .findFirst();

            if (optionalUser.isPresent()) {
                User userToRemove = optionalUser.get();

                existingEvent.getUsers().remove(userToRemove);
                existingEvent.setParticipantLimit(existingEvent.getParticipantLimit() + 1);

                eventRepository.save(existingEvent);

                return existingEvent.getDto();
            } else {
                throw new UserNotFoundException("User not found in the event with id: " + userId);
            }
        } else {
            throw new EventNotFoundException("Event not found with id: " + eventId);
        }
    }
}
