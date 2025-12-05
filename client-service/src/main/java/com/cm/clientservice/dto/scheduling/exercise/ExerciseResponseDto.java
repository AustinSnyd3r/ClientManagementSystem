package com.cm.clientservice.dto.scheduling.exercise;

import com.cm.clientservice.dto.scheduling.movement.MovementResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseResponseDto {
    private String id;
    private MovementResponseDto movement;
    private String numSets;
    private String numReps;
    private String coachNotes;
}
