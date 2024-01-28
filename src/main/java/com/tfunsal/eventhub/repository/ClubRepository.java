package com.tfunsal.eventhub.repository;

import com.tfunsal.eventhub.models.Club;
import com.tfunsal.eventhub.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findByName(String name);

    List<Club> findClubsByClubAdminId(Long clubAdminId);

    Club findClubByIdAndClubAdminId(Long clubId, Long clubAdminId);

}
