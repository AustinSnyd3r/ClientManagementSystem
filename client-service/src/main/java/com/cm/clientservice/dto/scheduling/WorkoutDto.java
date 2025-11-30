package com.cm.clientservice.dto.scheduling;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkoutDto {

    private String id;

    private List<ExerciseDto> exercises;

    private String date;
    private String workoutNotes;
    private String isCompleted;
}
