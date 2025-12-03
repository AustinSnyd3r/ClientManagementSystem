package com.cm.clientservice.dto.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgreementTemplateRequestDto {

    @NotBlank
    private String templateName;

    @NotNull
    @PositiveOrZero
    private String paymentAmount;

    @NotNull
    @Positive
    private String daysBetweenPayments;

    @NotBlank
    private String termsAndConditions;

    @NotBlank
    private String allowPublicTemplateReuse;
}
