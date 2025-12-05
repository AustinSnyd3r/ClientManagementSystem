package com.cm.clientservice.dto.contract;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractAgreementStatusRequestDto {
    @NotBlank
    private Boolean userIsInAgreement;
}
