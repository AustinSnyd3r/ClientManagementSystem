package com.cm.clientservice.service;

import com.cm.clientservice.dto.scheduling.movement.MovementCreateRequestDto;
import com.cm.clientservice.dto.scheduling.movement.MovementResponseDto;
import com.cm.clientservice.exception.MovementNotFoundException;
import com.cm.clientservice.mapper.schedule.MovementMapper;
import com.cm.clientservice.model.schedule.Movement;
import com.cm.clientservice.repository.MovementRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovementService {

    private final MovementRepository movementRepository;

    public MovementService(MovementRepository movementRepository){
        this.movementRepository = movementRepository;
    }

    public MovementResponseDto createMovement(MovementCreateRequestDto movementCreateRequestDto) {
        Movement movement = new Movement();
        movement.setName(movementCreateRequestDto.getName());
        movement.setDescription(movementCreateRequestDto.getDescription());

        Movement newMovement = movementRepository.save(movement);

        return MovementMapper.toDto(newMovement);
    }

    public MovementResponseDto getMovementById(UUID movementId) {
        Movement movement = movementRepository.findById(movementId).orElseThrow(
                () -> new MovementNotFoundException("Movement not found with id: " + movementId));

        return MovementMapper.toDto(movement);
    }
}
