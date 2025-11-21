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
public class CoachProfile {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToMany(mappedBy = "coaches")
    private List<ClientProfile> clients;

    @NotNull
    private String biography;
}