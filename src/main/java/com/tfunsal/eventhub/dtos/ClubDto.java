package com.tfunsal.eventhub.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ClubDto {

    private Long id;
    private String name;
    private String description;
    private String photoUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<ClubEventInfoDto> events = new ArrayList<>();
    private Long ClubAdminId;
}
