package com.tfunsal.eventhub.models;

import com.tfunsal.eventhub.dtos.EventDto;
import com.tfunsal.eventhub.dtos.UserDto;
import com.tfunsal.eventhub.enums.EventCategory;
import com.tfunsal.eventhub.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Long participantLimit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Club club;
    @OneToMany(cascade = CascadeType.ALL ,orphanRemoval = true)
    @JoinColumn(name = "user_Id")
    private List<User> users = new ArrayList<>();


    public EventDto getDto() {
        EventDto eventDto = new EventDto();

        eventDto.setId(id);
        eventDto.setName(name);
        eventDto.setLocation(location);
        eventDto.setStartTime(startTime);
        eventDto.setEndTime(endTime);
        eventDto.setEventCategory(eventCategory);
        eventDto.setEventType(eventType);
        eventDto.setParticipantLimit(participantLimit);
        eventDto.setClubId(club.getId());
        eventDto.setClubName(club.getName());

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users){
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDtoList.add(userDto);
        }
        eventDto.setUsers(userDtoList);

        return eventDto;
    }
}
