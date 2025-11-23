package com.cm.billingservice.service;

import com.cm.billingservice.dto.AccountRegistrationRequestDto;
import com.cm.billingservice.dto.AccountRegistrationResponseDto;
import com.cm.billingservice.dto.activation.ActivateCoachRequestDto;
import com.cm.billingservice.dto.activation.ActivateCoachResponseDto;
import com.cm.billingservice.exception.EmailAlreadyInUseException;
import com.cm.billingservice.mapper.AccountMapper;
import com.cm.billingservice.model.BillingAccount;
import com.cm.billingservice.repository.BillingAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BillingService {

    private WebClient webClient;
    private final BillingAccountRepository billingAccountRepository;

    public BillingService(BillingAccountRepository billingAccountRepository){
        this.billingAccountRepository = billingAccountRepository;
    }

    public AccountRegistrationResponseDto registerAccount(AccountRegistrationRequestDto accountRegistrationRequestDto){
        if(billingAccountRepository.existsByEmail(accountRegistrationRequestDto.getEmail())){
            throw new EmailAlreadyInUseException();
        }

        BillingAccount newAccount = new BillingAccount();
        newAccount.setEmail(accountRegistrationRequestDto.getEmail());
        newAccount.setIsActiveForPayment(false);

        BillingAccount createdAccount =
                billingAccountRepository.save(newAccount);

        return AccountMapper.toDto(createdAccount);
    }

    public ActivateCoachResponseDto activateCoach(
            ActivateCoachRequestDto activateCoachRequestDto){

        // Check if email is actually in use.
        //TODO: Implement me.
        return null;
    }


}
