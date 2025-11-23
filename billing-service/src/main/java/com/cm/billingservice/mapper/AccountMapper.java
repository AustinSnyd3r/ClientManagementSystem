package com.cm.billingservice.mapper;

import com.cm.billingservice.dto.AccountRegistrationResponseDto;
import com.cm.billingservice.model.BillingAccount;

public class AccountMapper {

    public static AccountRegistrationResponseDto toDto(BillingAccount account){
        AccountRegistrationResponseDto responseDto =
                new AccountRegistrationResponseDto();

        responseDto.setEmail(account.getEmail());
        responseDto.setIsActiveForPayment(account.getIsActiveForPayment());

        return responseDto;
    }
}
