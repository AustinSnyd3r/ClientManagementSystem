package com.cm.authservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private final String email;

    public UserResponseDto(String email){
        this.email = email;
    }
}
