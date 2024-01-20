package com.tfunsal.eventhub.services;

import com.tfunsal.eventhub.dtos.ClubCreateDto;
import com.tfunsal.eventhub.dtos.ClubDto;
import com.tfunsal.eventhub.dtos.ClubUpdateDto;

import java.util.List;

public interface ClubService {

    List<ClubDto> getAllClubs();

    ClubDto getClubById(Long id);

    ClubDto getClubByName(String name);

    ClubDto createClub(ClubCreateDto clubCreateDto);

    ClubDto updateClub(Long clubId, ClubUpdateDto clubUpdateDto);

    boolean deleteClub(Long id);
}
