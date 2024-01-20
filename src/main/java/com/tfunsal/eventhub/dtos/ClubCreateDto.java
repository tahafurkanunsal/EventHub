package com.tfunsal.eventhub.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClubCreateDto {

    private Long id;
    private String name;
    private String description;
    private String photoUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
