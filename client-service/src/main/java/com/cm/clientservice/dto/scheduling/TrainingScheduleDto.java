package com.cm.clientservice.dto.scheduling;

import com.cm.clientservice.dto.scheduling.workout.WorkoutResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainingScheduleDto {

    private String id;
    private List<WorkoutResponseDto> workouts;
}
