package com.cm.clientservice.dto.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachClientAgreementResponseDto {
    private String id;

    private String clientId;
    private String coachId;

    private String startDate;
    private AgreementTemplateResponseDto agreementTemplateResponseDto;

    private String clientIsInAgreement;
    private String coachIsInAgreement;
}
