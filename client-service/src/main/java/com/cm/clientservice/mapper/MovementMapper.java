package com.cm.clientservice.mapper;
import com.cm.clientservice.dto.scheduling.MovementDto;
import com.cm.clientservice.model.schedule.Movement;

public class MovementMapper {

    public static MovementDto toDto(Movement movement){
        MovementDto dto = new MovementDto();
        dto.setId(movement.getId().toString());
        dto.setName(movement.getName());
        dto.setDescription(movement.getDescription());
        return dto;
    }
}
