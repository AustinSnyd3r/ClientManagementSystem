package com.cm.billingservice.dto.activation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ActivateClientRequestDto {
    @Email
    @NotBlank
    private String email;

}
