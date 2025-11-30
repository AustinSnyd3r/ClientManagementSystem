package com.cm.clientservice.mapper;

import com.cm.clientservice.dto.scheduling.ExerciseDto;
import com.cm.clientservice.model.schedule.Exercise;

import java.util.List;

public class ExerciseMapper {
    public static List<ExerciseDto> toDto(List<Exercise> exercises) {
        return exercises.stream()
                .map(ExerciseMapper::toDto)
                .toList();
    }

    public static ExerciseDto toDto(Exercise exercise){
        ExerciseDto dto = new ExerciseDto();

        dto.setId(exercise.getId().toString());
        dto.setMovement(MovementMapper.toDto(exercise.getMovement()));
        dto.setCoachNotes(exercise.getCoachNotes());
        dto.setNumReps(String.valueOf(exercise.getNumReps()));
        dto.setNumSets(String.valueOf(exercise.getNumSets()));
        return dto;
    }
}
