package com.cm.clientservice.mapper.contract;
import com.cm.clientservice.dto.contract.CoachClientAgreementRequestDto;
import com.cm.clientservice.dto.contract.CoachClientAgreementResponseDto;
import com.cm.clientservice.model.contract.CoachClientAgreement;

import java.time.LocalDate;

public class CoachClientAgreementMapper {
    public static CoachClientAgreementResponseDto toDto(CoachClientAgreement agreement) {
        CoachClientAgreementResponseDto dto = new CoachClientAgreementResponseDto();

        dto.setAgreementTemplateResponseDto(AgreementTemplateMapper.toDto(agreement.getAgreementTemplate()));
        dto.setClientId(agreement.getClient().getId().toString());
        dto.setCoachId(agreement.getCoach().getId().toString());
        dto.setStartDate(agreement.getStartDate().toString());
        dto.setId(agreement.getId().toString());
        dto.setClientIsInAgreement(agreement.getClientIsInAgreement().toString());
        dto.setCoachIsInAgreement(agreement.getCoachIsInAgreement().toString());

        return dto;
    }

    public static CoachClientAgreement toModel(CoachClientAgreementRequestDto dto){
        CoachClientAgreement agreement = new CoachClientAgreement();

        agreement.setClientIsInAgreement(Boolean.FALSE);
        agreement.setCoachIsInAgreement(Boolean.TRUE);
        agreement.setStartDate(LocalDate.parse(dto.getStartDate()));

        return agreement;
    }
}
