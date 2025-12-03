package com.cm.clientservice.mapper.contract;

import com.cm.clientservice.dto.contract.AgreementTemplateRequestDto;
import com.cm.clientservice.dto.contract.AgreementTemplateResponseDto;
import com.cm.clientservice.model.contract.AgreementTemplate;

public class AgreementTemplateMapper {

    public static AgreementTemplateResponseDto toDto(AgreementTemplate agreementTemplate){
        AgreementTemplateResponseDto dto = new AgreementTemplateResponseDto();

        dto.setId(agreementTemplate.getId().toString());
        dto.setVersion(agreementTemplate.getVersion().toString());
        dto.setPaymentAmount(agreementTemplate.getPaymentAmount().toString());
        dto.setTermsAndConditions(agreementTemplate.getTermsAndConditions());
        dto.setDaysBetweenPayments(agreementTemplate.getDaysBetweenPayments().toString());
        dto.setAuthorId(agreementTemplate.getAuthor().getId().toString());
        dto.setTemplateName(agreementTemplate.getTemplateName());

        return dto;
    }

    public static AgreementTemplate toModel(AgreementTemplateRequestDto dto) {
        AgreementTemplate template = new AgreementTemplate();

        template.setPaymentAmount(Double.parseDouble(dto.getPaymentAmount()));
        template.setTermsAndConditions(dto.getTermsAndConditions());
        template.setAllowPublicTemplateReuse(Boolean.valueOf(dto.getAllowPublicTemplateReuse()));
        template.setDaysBetweenPayments(Integer.parseInt(dto.getDaysBetweenPayments()));
        template.setTemplateName(dto.getTemplateName());

        return template;
    }
}
