package com.cm.clientservice.model.schedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Movement {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Exercise must have a name.")
    @Column(unique = true) // make users build this up basically.
    private String name;

    @NotNull
    private String description;
}

