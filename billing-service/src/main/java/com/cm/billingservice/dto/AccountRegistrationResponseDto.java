package com.cm.billingservice.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRegistrationResponseDto {
    private String email;
    private Boolean isActiveForPayment;
}
