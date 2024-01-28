package com.tfunsal.eventhub.models;

import com.tfunsal.eventhub.dtos.ClubDto;
import com.tfunsal.eventhub.dtos.ClubEventInfoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "club")
    private List<Event> events = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_admin_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User clubAdmin;


    public ClubDto getDto() {
        ClubDto clubDto = new ClubDto();

        clubDto.setId(id);
        clubDto.setName(name);
        clubDto.setDescription(description);
        clubDto.setPhotoUrl(photoUrl);
        clubDto.setCreatedDate(createdDate);
        clubDto.setUpdatedDate(updatedDate);
        clubDto.setClubAdminId(clubAdmin.getId());

        List<ClubEventInfoDto> clubEventInfoDtos = new ArrayList<>();
        for (Event event : events) {
            ClubEventInfoDto clubEventInfoDto = new ClubEventInfoDto();
            clubEventInfoDto.setName(event.getName());
            clubEventInfoDto.setLocation(event.getLocation());
            clubEventInfoDto.setStartTime(event.getStartTime());
            clubEventInfoDto.setEndTime(event.getEndTime());
            clubEventInfoDto.setEventCategory(event.getEventCategory());
            clubEventInfoDto.setEventType(event.getEventType());
            clubEventInfoDto.setParticipantLimit(event.getParticipantLimit());
            clubEventInfoDtos.add(clubEventInfoDto);
        }
        clubDto.setEvents(clubEventInfoDtos);
        return clubDto;
    }
}
