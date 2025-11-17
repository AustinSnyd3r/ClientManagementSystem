package com.cm.clientservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String address;
    private String dateOfBirth;
}
