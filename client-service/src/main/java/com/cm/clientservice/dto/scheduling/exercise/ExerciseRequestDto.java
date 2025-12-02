package com.cm.clientservice.dto.scheduling.exercise;
import com.cm.clientservice.dto.scheduling.movement.MovementReuseRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRequestDto {
    private MovementReuseRequestDto movement;
    private String numSets;
    private String numReps;
    private String coachNotes;
}
