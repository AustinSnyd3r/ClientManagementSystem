package com.cm.clientservice.mapper.schedule;

import com.cm.clientservice.dto.scheduling.workout.WorkoutRequestDto;
import com.cm.clientservice.dto.scheduling.workout.WorkoutResponseDto;
import com.cm.clientservice.model.schedule.Exercise;
import com.cm.clientservice.model.schedule.Workout;

import java.time.LocalDate;
import java.util.List;

public class WorkoutMapper {

    public static List<WorkoutResponseDto> toDto(List<Workout> workouts){
        return workouts
                .stream()
                .map(WorkoutMapper::toDto)
                .toList();
    }

    public static WorkoutResponseDto toDto(Workout workout){
        WorkoutResponseDto dto = new WorkoutResponseDto();

        dto.setId(String.valueOf(workout.getId()));
        dto.setDate(workout.getDate().toString());
        dto.setWorkoutNotes(workout.getWorkoutNotes());
        dto.setIsCompleted(workout.getIsCompleted().toString());
        dto.setExercises(ExerciseMapper.toDto(workout.getExercises()));

        return dto;
    }

    public static Workout toModel(WorkoutRequestDto workoutRequestDto){
        Workout workout = new Workout();

        workout.setIsCompleted(Boolean.valueOf(workoutRequestDto.getIsCompleted()));
        workout.setDate(LocalDate.parse(workoutRequestDto.getDate()));
        workout.setWorkoutNotes(workoutRequestDto.getWorkoutNotes());

        List<Exercise> exercises = ExerciseMapper.toModel(workoutRequestDto.getExercises());
        for (Exercise e : exercises) e.setWorkout(workout);
        workout.setExercises(exercises);

        return workout;
    }
}
