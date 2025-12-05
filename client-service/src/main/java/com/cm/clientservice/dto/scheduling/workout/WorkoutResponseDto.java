package com.cm.clientservice.dto.scheduling.workout;

import com.cm.clientservice.dto.scheduling.exercise.ExerciseResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkoutResponseDto {
    private String id;
    private List<ExerciseResponseDto> exercises;
    private String date;
    private String workoutNotes;
    private String isCompleted;
}
