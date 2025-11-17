package com.cm.authservice.dto;

import lombok.Getter;

@Getter
public class RegistrationResponseDTO {
    private final String email;

    public RegistrationResponseDTO(String email){
        this.email = email;
    }
}
