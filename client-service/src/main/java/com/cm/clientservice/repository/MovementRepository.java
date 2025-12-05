package com.cm.clientservice.repository;

import com.cm.clientservice.model.schedule.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovementRepository extends JpaRepository<Movement, UUID> {
    Optional<Movement> findMovementByName(String name);
}
