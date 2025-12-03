package com.cm.clientservice.dto.contract;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachClientAgreementRequestDto {
    @NotNull
    private String clientId;
    @NotNull
    private String startDate;
    @NotNull
    private AgreementTemplateRequestDto agreementTemplateDto;
}
