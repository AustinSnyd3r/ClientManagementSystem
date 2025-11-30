package com.cm.clientservice.dto.scheduling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDto {

    private String id;
    private MovementDto movement;
    private String numSets;
    private String numReps;
    private String coachNotes;
}
