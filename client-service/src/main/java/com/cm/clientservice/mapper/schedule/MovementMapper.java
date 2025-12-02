package com.cm.clientservice.mapper.schedule;
import com.cm.clientservice.dto.scheduling.movement.MovementResponseDto;
import com.cm.clientservice.dto.scheduling.movement.MovementReuseRequestDto;
import com.cm.clientservice.model.schedule.Movement;

import java.util.UUID;

public class MovementMapper {

    public static MovementResponseDto toDto(Movement movement){
        MovementResponseDto dto = new MovementResponseDto();
        dto.setId(movement.getId().toString());
        dto.setName(movement.getName());
        dto.setDescription(movement.getDescription());
        return dto;
    }

    public static Movement toModel(MovementReuseRequestDto movementDto) {
        Movement movement = new Movement();

        movement.setId(UUID.fromString(movementDto.getId()));

        return movement;
    }
}
