package com.cm.clientservice.model.schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class TrainingSchedule {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "trainingSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workout> workouts = new ArrayList<>();
}
