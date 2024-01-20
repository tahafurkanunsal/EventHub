package com.tfunsal.eventhub.services;

import com.tfunsal.eventhub.dtos.ClubDto;

import java.util.List;

public interface ClubService {

    List<ClubDto> getAllClubs();

    ClubDto getClubById(Long id);

    ClubDto getClubByName(String name);

    ClubDto getClubEvents(Long id);

    ClubDto createClub(ClubDto clubDto);

    ClubDto updateClub(Long clubId , ClubDto clubDto);

    boolean deleteClub(Long id);
}
