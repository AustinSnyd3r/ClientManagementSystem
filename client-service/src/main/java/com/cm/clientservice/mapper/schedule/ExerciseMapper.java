package com.cm.clientservice.mapper.schedule;
import com.cm.clientservice.dto.scheduling.exercise.ExerciseRequestDto;
import com.cm.clientservice.dto.scheduling.exercise.ExerciseResponseDto;
import com.cm.clientservice.model.schedule.Exercise;

import java.util.List;

public class ExerciseMapper {
    public static List<ExerciseResponseDto> toDto(List<Exercise> exercises) {
        return exercises.stream()
                .map(ExerciseMapper::toDto)
                .toList();
    }

    public static ExerciseResponseDto toDto(Exercise exercise){
        ExerciseResponseDto dto = new ExerciseResponseDto();

        dto.setId(exercise.getId().toString());
        dto.setMovement(MovementMapper.toDto(exercise.getMovement()));
        dto.setCoachNotes(exercise.getCoachNotes());
        dto.setNumReps(String.valueOf(exercise.getNumReps()));
        dto.setNumSets(String.valueOf(exercise.getNumSets()));
        return dto;
    }

    public static List<Exercise> toModel(List<ExerciseRequestDto> exerciseDtos) {
        return exerciseDtos.stream()
                .map(ExerciseMapper::toModel)
                .toList();
    }

    public static Exercise toModel(ExerciseRequestDto exerciseDto){
        Exercise exercise = new Exercise();

        exercise.setCoachNotes(exerciseDto.getCoachNotes());
        exercise.setMovement(MovementMapper.toModel(exerciseDto.getMovement()));
        exercise.setNumSets(Integer.parseInt(exerciseDto.getNumSets()));
        exercise.setNumReps(Integer.parseInt(exerciseDto.getNumReps()));

        return exercise;
    }
}
