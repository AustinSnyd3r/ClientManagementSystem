package com.cm.clientservice.dto.coaching;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientViewResponseDto {

    private ClientAgreementSnippetDto clientAgreementSnippetDto;

    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String dateOfBirth;
}
