package com.cm.clientservice.mapper.contract;
import com.cm.clientservice.dto.contract.CoachClientAgreementDto;
import com.cm.clientservice.model.contract.CoachClientAgreement;

public class CoachClientAgreementMapper {
    public static CoachClientAgreementDto toDto(CoachClientAgreement agreement) {
        CoachClientAgreementDto dto = new CoachClientAgreementDto();

        dto.setAgreementTemplateDto(AgreementTemplateMapper.toDto(agreement.getAgreementTemplate()));
        dto.setClientId(agreement.getClient().getId().toString());
        dto.setCoachId(agreement.getCoach().getId().toString());
        dto.setStartDate(agreement.getStartDate().toString());

        return dto;
    }
}
