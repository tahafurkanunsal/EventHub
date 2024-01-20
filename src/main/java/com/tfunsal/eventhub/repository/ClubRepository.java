package com.tfunsal.eventhub.repository;

import com.tfunsal.eventhub.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findByName(String name);

}
