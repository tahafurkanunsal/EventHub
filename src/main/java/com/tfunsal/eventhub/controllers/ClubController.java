package com.tfunsal.eventhub.controllers;

import com.tfunsal.eventhub.dtos.ClubCreateDto;
import com.tfunsal.eventhub.dtos.ClubDto;
import com.tfunsal.eventhub.dtos.ClubUpdateDto;
import com.tfunsal.eventhub.models.User;
import com.tfunsal.eventhub.services.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/clubs")
    public ResponseEntity<List<ClubDto>> getAllClubs() {
        List<ClubDto> clubDtoList = clubService.getAllClubs();
        return ResponseEntity.ok(clubDtoList);
    }

    @GetMapping(value = "/clubs", params = {"id"})
    public ResponseEntity<ClubDto> getClubById(@RequestParam Long id) {
        ClubDto clubDto = clubService.getClubById(id);
        return ResponseEntity.ok(clubDto);
    }

    @GetMapping(value = "/clubs", params = {"name"})
    public ResponseEntity<ClubDto> getClubByName(@RequestParam String name) {
        ClubDto clubDto = clubService.getClubByName(name);
        return ResponseEntity.ok(clubDto);
    }

    @GetMapping(value = "/admin/clubs")
    public ResponseEntity<List<ClubDto>> getClubsByClubAdmin(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        List<ClubDto> clubDtoList = clubService.getClubsByClubAdminId(userId);
        if (clubDtoList != null) {
            return ResponseEntity.ok(clubDtoList);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(value = "/admin/clubs", params = {"clubId"})
    public ResponseEntity<ClubDto> getClubByClubIdAndClubAdminId(@RequestParam Long clubId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        ClubDto clubDto = clubService.getClubByClubIdAndClubAdminId(clubId, userId);
        if (clubDto != null) {
            return ResponseEntity.ok(clubDto);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/clubs")
    public ResponseEntity<ClubDto> createClub(@RequestBody ClubCreateDto clubCreateDto) {
        ClubDto newClub = clubService.createClub(clubCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClub);
    }

    @PutMapping("/clubs/{clubId}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable Long clubId, @RequestBody ClubUpdateDto clubUpdateDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        ClubDto clubDto = clubService.getClubByClubIdAndClubAdminId(clubId, userId);
        if (clubDto != null) {
            ClubDto updateClub = clubService.updateClub(clubId, clubUpdateDto);
            return ResponseEntity.ok(updateClub);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping(value = "/clubs", params = {"id"})
    public ResponseEntity<Void> deleteClub(@RequestParam Long id) {
        boolean deletedClub = clubService.deleteClub(id);

        if (deletedClub) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
