package com.cm.clientservice.mapper.contract;

import com.cm.clientservice.dto.contract.AgreementTemplateDto;
import com.cm.clientservice.model.contract.AgreementTemplate;

public class AgreementTemplateMapper {

    public static AgreementTemplateDto toDto(AgreementTemplate agreementTemplate){
        AgreementTemplateDto dto = new AgreementTemplateDto();

        dto.setId(agreementTemplate.getId().toString());
        dto.setVersion(agreementTemplate.getVersion().toString());
        dto.setPaymentAmount(agreementTemplate.getPaymentAmount().toString());
        dto.setTermsAndConditions(agreementTemplate.getTermsAndConditions());
        dto.setDaysBetweenPayments(agreementTemplate.getDaysBetweenPayments().toString());
        dto.setAuthorId(agreementTemplate.getAuthor().getId().toString());

        return dto;
    }
}
