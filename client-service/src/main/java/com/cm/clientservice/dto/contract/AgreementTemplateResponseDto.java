package com.cm.clientservice.dto.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgreementTemplateResponseDto {
    private String id;
    private String templateName;
    private String authorId;
    private String paymentAmount;
    private String daysBetweenPayments;
    private String termsAndConditions;
    private String version;
}
