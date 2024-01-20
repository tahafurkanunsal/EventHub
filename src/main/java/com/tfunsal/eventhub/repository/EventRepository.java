package com.tfunsal.eventhub.repository;

import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;
import com.tfunsal.eventhub.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findEventByClubId(Long clubId);

    List<Event> findEventsByName(String eventName);

    List<Event> findEventsByEventCategory(EventCategory eventCategory);

    List<Event> findEventsByEventType(EventType eventType);

    List<Event> findEventsByLocation(String location);

    List<Event> findEventsByClubId(Long clubId);
}
