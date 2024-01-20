package com.tfunsal.eventhub.models;

import com.tfunsal.eventhub.dtos.ClubDto;
import com.tfunsal.eventhub.dtos.EventDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clubs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String photoUrl;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<Event> events = new ArrayList<>();


    public ClubDto getDto() {
        ClubDto clubDto = new ClubDto();

        clubDto.setId(id);
        clubDto.setName(name);
        clubDto.setDescription(description);
        clubDto.setPhotoUrl(photoUrl);
        clubDto.setCreatedDate(createdDate);
        clubDto.setUpdatedDate(updatedDate);

        List<EventDto> eventDtos = new ArrayList<>();
        for (Event event : events) {
            EventDto eventDto = new EventDto();
            eventDto.setId(event.getId());
            eventDto.setName(event.getName());
            eventDto.setLocation(event.getLocation());
            eventDto.setStartTime(event.getStartTime());
            eventDto.setEndTime(event.getEndTime());
            eventDto.setEventCategory(event.getEventCategory());
            eventDto.setEventType(event.getEventType());
            eventDto.setClubId(event.getClub().getId());
            eventDtos.add(eventDto);
        }
        clubDto.setEvents(eventDtos);
        return clubDto;
    }
}
