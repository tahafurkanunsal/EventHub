package com.tfunsal.eventhub.repository;

import com.tfunsal.eventhub.models.Role;
import com.tfunsal.eventhub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
