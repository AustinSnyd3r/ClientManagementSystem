package com.cm.clientservice.model.schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class WorkoutSchedule {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToMany
    @JoinTable(name = "trainingschedule_workout",
            joinColumns = @JoinColumn(name = "trainingschedule_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id"))
    private List<Workout> workouts;
}
