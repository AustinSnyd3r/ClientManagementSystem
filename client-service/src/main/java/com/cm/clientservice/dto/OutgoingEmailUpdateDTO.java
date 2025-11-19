package com.cm.clientservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutgoingEmailUpdateDTO {
    private String oldEmail;
    private String newEmail;
}
