package com.tfunsal.eventhub.controllers;

import com.tfunsal.eventhub.dtos.EventDto;
import com.tfunsal.eventhub.dtos.EventUpdateDto;
import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;
import com.tfunsal.eventhub.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<List<EventDto>> getAllEvent() {
        List<EventDto> eventDtoList = eventService.getAllEvent();
        return ResponseEntity.ok(eventDtoList);
    }

    @GetMapping(value = "/events", params = {"id"})
    public ResponseEntity<EventDto> getEventById(@RequestParam Long id) {
        EventDto eventDto = eventService.getEventById(id);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping(value = "/events", params = {"name"})
    public ResponseEntity<List<EventDto>> getEventsByName(@RequestParam String name) {
        List<EventDto> eventDtoList = eventService.getEventsByName(name);
        return ResponseEntity.ok(eventDtoList);
    }

    @GetMapping(value = "/events", params = {"eventCategory"})
    public ResponseEntity<List<EventDto>> getEventsByEventCategory(@RequestParam EventCategory eventCategory) {
        List<EventDto> eventDtoList = eventService.getEventsByEventCategory(eventCategory);
        return ResponseEntity.ok(eventDtoList);
    }

    @GetMapping(value = "/events", params = {"eventType"})
    public ResponseEntity<List<EventDto>> getEventsByEventType(@RequestParam EventType eventType) {
        List<EventDto> eventDtoList = eventService.getEventsByEventType(eventType);
        return ResponseEntity.ok(eventDtoList);
    }

    @GetMapping(value = "/events", params = {"location"})
    public ResponseEntity<List<EventDto>> getEventsByLocation(@RequestParam String location) {
        List<EventDto> eventDtoList = eventService.getEventsByLocation(location);
        return ResponseEntity.ok(eventDtoList);
    }

    @GetMapping(value = "/events/{clubId}")
    public ResponseEntity<List<EventDto>> getEventsByClub(@PathVariable("clubId") Long clubId) {
        List<EventDto> eventDtoList = eventService.getEventsByClub(clubId);
        return ResponseEntity.ok(eventDtoList);
    }

    @PostMapping("/events")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        EventDto newEvent = eventService.createEvent(eventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable("id") Long id, EventUpdateDto eventUpdateDto) {
        EventDto updateEvent = eventService.updateEvent(id, eventUpdateDto);
        return ResponseEntity.ok(updateEvent);
    }

    @DeleteMapping(value = "/events", params = {"id"})
    public ResponseEntity<Void> deleteEvent(@RequestParam Long id) {
        boolean deletedEvent = eventService.deleteEvent(id);
        if (deletedEvent) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
