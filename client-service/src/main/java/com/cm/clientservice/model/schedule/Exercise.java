package com.cm.clientservice.model.schedule;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Exercise {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="movement_id")
    private Movement movement;

    @NotNull
    private int numSets;

    @NotNull
    private int numReps;

    @NotNull
    private String coachNotes;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
}
