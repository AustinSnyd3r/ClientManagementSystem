package com.cm.clientservice.dto.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgreementTemplateDto {
    private String id;
    private String authorId;
    private String paymentAmount;
    private String daysBetweenPayments;
    private String termsAndConditions;
    private String version;
}
