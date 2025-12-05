package com.cm.clientservice.dto.scheduling.workout;
import com.cm.clientservice.dto.scheduling.exercise.ExerciseRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class WorkoutRequestDto {
    @NotNull(message = "Exercises should not be null.")
    private List<ExerciseRequestDto> exercises;

    private String date;

    @NotNull(message = "Workout notes should not be null.")
    private String workoutNotes;

    @NotBlank(message = "Completion flag should not be blank.")
    private String isCompleted;

    @NotNull
    private String trainingScheduleId;
}
