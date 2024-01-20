package com.tfunsal.eventhub.models;

import com.tfunsal.eventhub.dtos.EventDto;
import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventCategory eventCategory;
    private EventType eventType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "club_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;


    public EventDto getDto() {
        EventDto eventDto = new EventDto();

        eventDto.setId(id);
        eventDto.setName(name);
        eventDto.setLocation(location);
        eventDto.setStartTime(startTime);
        eventDto.setEndTime(endTime);
        eventDto.setEventCategory(eventCategory);
        eventDto.setEventType(eventType);
        eventDto.setClubId(club.getId());
        eventDto.setClubName(club.getName());

        return eventDto;
    }


}
