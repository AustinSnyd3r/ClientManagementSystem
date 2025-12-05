package com.cm.clientservice.dto.contract;
import com.cm.clientservice.dto.contract.template.AgreementTemplateReuseRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachClientAgreementRequestDto {
    @NotNull
    private String startDate;
    @NotNull
    private AgreementTemplateReuseRequestDto template;
}
