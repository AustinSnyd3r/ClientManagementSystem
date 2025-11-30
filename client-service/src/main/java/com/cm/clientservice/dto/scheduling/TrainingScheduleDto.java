package com.cm.clientservice.dto.scheduling;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainingScheduleDto {

    private String id;
    private List<WorkoutDto> workouts;
}
