package com.cm.clientservice.model.schedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Workout {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

    @NotNull
    private LocalDate date;

    @NotNull
    private String workoutNotes;

    @NotNull
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "training_schedule_id")
    private TrainingSchedule trainingSchedule;
}
