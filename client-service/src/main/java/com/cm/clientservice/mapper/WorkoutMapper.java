package com.cm.clientservice.mapper;

import com.cm.clientservice.dto.scheduling.WorkoutDto;
import com.cm.clientservice.model.schedule.Workout;

import java.util.List;
public class WorkoutMapper {

    public static List<WorkoutDto> toDto(List<Workout> workouts){
        return workouts
                .stream()
                .map(WorkoutMapper::toDto)
                .toList();
    }

    public static WorkoutDto toDto(Workout workout){
        WorkoutDto dto = new WorkoutDto();

        dto.setId(String.valueOf(workout.getId()));
        dto.setDate(workout.getDate().toString());
        dto.setWorkoutNotes(workout.getWorkoutNotes());
        dto.setIsCompleted(workout.getIsCompleted().toString());
        dto.setExercises(ExerciseMapper.toDto(workout.getExercises()));

        return dto;
    }
}
