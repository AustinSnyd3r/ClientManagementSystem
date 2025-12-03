package com.cm.clientservice.dto.scheduling.exercise;
import com.cm.clientservice.dto.scheduling.movement.MovementReuseRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRequestDto {
    @NotNull
    private MovementReuseRequestDto movement;
    @NotBlank
    private String numSets;
    @NotBlank
    private String numReps;
    @NotNull
    private String coachNotes;
}