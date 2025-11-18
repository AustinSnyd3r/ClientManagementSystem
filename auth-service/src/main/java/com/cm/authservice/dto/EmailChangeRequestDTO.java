package com.cm.authservice.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailChangeRequestDTO {
    @Email
    private String oldEmail;

    @Email
    private String newEmail;
}
