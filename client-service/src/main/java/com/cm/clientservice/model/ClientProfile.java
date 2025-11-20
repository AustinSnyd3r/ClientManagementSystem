package com.cm.clientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ClientProfile {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "client_coach_map",
            joinColumns = @JoinColumn(name = "client_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_profile_id")
    )
    private List<CoachProfile> coaches;
}
