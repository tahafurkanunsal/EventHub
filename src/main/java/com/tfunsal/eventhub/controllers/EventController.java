package com.tfunsal.eventhub.controllers;

import com.tfunsal.eventhub.dtos.ClubDto;
import com.tfunsal.eventhub.dtos.EventDto;
import com.tfunsal.eventhub.dtos.EventUpdateDto;
import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;
import com.tfunsal.eventhub.models.User;
import com.tfunsal.eventhub.services.ClubService;
import com.tfunsal.eventhub.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    private final ClubService clubService;

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

    @GetMapping(value = "/events" ,params = {"clubId"})
    public ResponseEntity<List<EventDto>> getEventsByClub(@RequestParam Long clubId) {
        List<EventDto> eventDtoList = eventService.getEventsByClub(clubId);
        return ResponseEntity.ok(eventDtoList);
    }

    @GetMapping(value = "/admin/events")
    public ResponseEntity<List<EventDto>> getEventsByClub(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        ClubDto clubDto = clubService.getClubsByClubAdminId(userId).get(0);

        if (clubDto != null) {
            List<EventDto> eventDtoList = eventService.getEventsByClub(clubDto.getId());
            return ResponseEntity.ok(eventDtoList);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(value = "/admin/events", params = {"eventId"})
    public ResponseEntity<EventDto> getEventByEventIdAndClubId(@RequestParam Long eventId, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        ClubDto clubDto = clubService.getClubsByClubAdminId(userId).get(0);
        if (clubDto != null) {
            EventDto eventDto = eventService.getEventByEventIdAndClubId(eventId, clubDto.getId());
            return ResponseEntity.ok(eventDto);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/events")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        EventDto newEvent = eventService.createEvent(eventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long eventId, @RequestBody EventUpdateDto eventUpdateDto, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        ClubDto clubDto = clubService.getClubsByClubAdminId(userId).get(0);

        EventDto eventDto = eventService.getEventByEventIdAndClubId(eventId, clubDto.getId());
        if (eventDto != null) {
            EventDto updateEvent = eventService.updateEvent(eventId, eventUpdateDto);
            return ResponseEntity.ok(updateEvent);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/events/{eventId}/join")
    public ResponseEntity<EventDto> joinTheEvents(@PathVariable Long eventId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        EventDto updateEvent = eventService.joinTheEvent(eventId, userId);
        return ResponseEntity.ok(updateEvent);
    }

    @PatchMapping("/events/{eventId}/leave")
    public ResponseEntity<EventDto> leaveTheEvents(@PathVariable Long eventId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        EventDto updateEvent = eventService.leaveTheEvent(eventId, userId);
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
