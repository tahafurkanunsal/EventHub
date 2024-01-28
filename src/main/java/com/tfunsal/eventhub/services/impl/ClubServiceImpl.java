package com.tfunsal.eventhub.services.impl;

import com.tfunsal.eventhub.dtos.ClubCreateDto;
import com.tfunsal.eventhub.dtos.ClubDto;
import com.tfunsal.eventhub.dtos.ClubUpdateDto;
import com.tfunsal.eventhub.models.Club;
import com.tfunsal.eventhub.models.Role;
import com.tfunsal.eventhub.models.User;
import com.tfunsal.eventhub.repository.ClubRepository;
import com.tfunsal.eventhub.repository.EventRepository;
import com.tfunsal.eventhub.repository.UserRepository;
import com.tfunsal.eventhub.services.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

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
    public List<ClubDto> getClubsByClubAdminId(Long clubAdminId) {
        List<Club> clubs = clubRepository.findClubsByClubAdminId(clubAdminId);
        return clubs.stream().map(Club::getDto).collect(Collectors.toList());
    }

    @Override
    public ClubDto getClubByClubIdAndClubAdminId(Long clubId, Long clubAdminId) {
        Club club = clubRepository.findClubByIdAndClubAdminId(clubId, clubAdminId);
        return club.getDto();
    }


    @Override
    @Transactional
    public ClubDto createClub(ClubCreateDto clubCreateDto) {

        User clubAdmin = new User();
        clubAdmin.setName(clubCreateDto.getClubAdminName());
        clubAdmin.setLastName(clubCreateDto.getClubAdminLastName());
        clubAdmin.setRole(Role.CLUB_ADMIN);
        clubAdmin.setEmail(clubCreateDto.getClubAdminEmail());
        clubAdmin.setPassword(new BCryptPasswordEncoder().encode(clubCreateDto.getClubAdminPassword()));

        userRepository.save(clubAdmin);

        Club club = new Club();
        club.setId(clubCreateDto.getId());
        club.setName(clubCreateDto.getName());
        club.setDescription(clubCreateDto.getDescription());
        club.setCreatedDate(LocalDateTime.now());
        club.setUpdatedDate(LocalDateTime.now());
        club.setPhotoUrl(clubCreateDto.getPhotoUrl());
        club.setClubAdmin(clubAdmin);

        return clubRepository.save(club).getDto();
    }

    @Override
    public ClubDto updateClub(Long clubId, ClubUpdateDto clubUpdateDto) {

        Club existingClub = clubRepository.findById(clubId).get();

        existingClub.setId(clubId);
        existingClub.setName(clubUpdateDto.getName());
        existingClub.setDescription(clubUpdateDto.getDescription());
        existingClub.setUpdatedDate(LocalDateTime.now());
        existingClub.setPhotoUrl(clubUpdateDto.getPhotoUrl());

        return clubRepository.save(existingClub).getDto();
    }

    @Override
    public boolean deleteClub(Long id) {

        Optional<Club> club = clubRepository.findById(id);
        if (club.isPresent()) {
            clubRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
