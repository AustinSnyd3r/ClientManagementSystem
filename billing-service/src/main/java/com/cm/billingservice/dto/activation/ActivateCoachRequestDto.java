package com.cm.billingservice.dto.activation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivateCoachRequestDto {
    @Email
    @NotBlank
    private String email;
}
