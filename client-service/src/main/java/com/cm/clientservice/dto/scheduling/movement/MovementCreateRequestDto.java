package com.cm.clientservice.dto.scheduling.movement;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementCreateRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String description;
}
