package com.tfunsal.eventhub.services.impl;

import com.tfunsal.eventhub.dtos.ClubDto;
import com.tfunsal.eventhub.models.Club;
import com.tfunsal.eventhub.models.Event;
import com.tfunsal.eventhub.repository.ClubRepository;
import com.tfunsal.eventhub.repository.EventRepository;
import com.tfunsal.eventhub.services.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    private final EventRepository eventRepository;

    @Override
    public List<ClubDto> getAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map(Club::getDto).collect(Collectors.toList());
    }

    @Override
    public ClubDto getClubById(Long id) {
        Club club = clubRepository.findById(id).orElseThrow();
        return club.getDto();

    }

    @Override
    public ClubDto getClubByName(String name) {
        Club club = clubRepository.findByName(name);
        return club.getDto();
    }

    @Override
    public ClubDto getClubEvents(Long id) {

        Optional<Club> club = clubRepository.findById(id);

        if (club.isPresent()){
            List<Event> events = (List<Event>) eventRepository.findEventByClubId(id);
            club.get().setEvents(events);
        }
        return club.get().getDto();
    }

    @Override
    public ClubDto createClub(ClubDto clubDto) {
        Club club = new Club();
        club.setId(clubDto.getId());
        club.setName(clubDto.getName());
        club.setDescription(clubDto.getDescription());
        club.setCreatedDate(clubDto.getCreatedDate());
        club.setUpdatedDate(clubDto.getUpdatedDate());
        club.setPhotoUrl(clubDto.getPhotoUrl());

        if (clubDto.getEvents() != null){
            List<Event> events = (List<Event>) eventRepository.findEventByClubId(clubDto.getId());
            club.setEvents(events);
        }else {
            club.setEvents(null);
        }
        return clubRepository.save(club).getDto();
    }

    @Override
    public ClubDto updateClub(Long clubId, ClubDto clubDto) {

        Club existingClub = clubRepository.findById(clubId).get();

            existingClub.setId(clubId);
            existingClub.setName(clubDto.getName());
            existingClub.setDescription(clubDto.getDescription());
            existingClub.setCreatedDate(clubDto.getCreatedDate());
            existingClub.setUpdatedDate(clubDto.getUpdatedDate());
            existingClub.setPhotoUrl(clubDto.getPhotoUrl());

            if (clubDto.getEvents() != null){
                List<Event> events = (List<Event>) eventRepository.findEventByClubId(clubDto.getId());
                existingClub.setEvents(events);
            }else {
                existingClub.setEvents(null);
            }

        return clubRepository.save(existingClub).getDto();
    }

    @Override
    public boolean deleteClub(Long id) {

        Optional<Club> club = clubRepository.findById(id);
        if (club.isPresent()){
            clubRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
