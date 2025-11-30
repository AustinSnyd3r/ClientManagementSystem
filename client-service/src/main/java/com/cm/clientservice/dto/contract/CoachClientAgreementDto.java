package com.cm.clientservice.dto.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachClientAgreementDto {
    private String coachId;
    private String clientId;
    private String startDate;
    private AgreementTemplateDto agreementTemplateDto;
}
