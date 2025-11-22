package com.cm.billingservice.controller;
import com.cm.billingservice.dto.AccountRegistrationRequestDto;
import com.cm.billingservice.dto.AccountRegistrationResponseDto;
import com.cm.billingservice.dto.activation.ActivateClientResponseDto;
import com.cm.billingservice.dto.activation.ActivateCoachResponseDto;
import com.cm.billingservice.service.BillingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private BillingService billingService;

    public BillingController(BillingService billingService){
        this.billingService = billingService;
    }


    /**
     *  This will be called to register the user to OUR backend.
     *
     *  This should be done when the user first creates their User profile
     *  even if they aren't buyig anything yet.
     */
    @PostMapping("/register")
    public AccountRegistrationResponseDto registerAccount(
            @RequestBody AccountRegistrationRequestDto billingAccountRegistrationRequestDto){

    }

    @PostMapping("/coach/activate")
    public ActivateCoachResponseDto activateCoach(@Value("${stripe.secret.key}") String stripeKey, @RequestBody){


    }

    @PostMapping("/client/activate")
    public ActivateClientResponseDto activateClient(@Value("${stripe.secret.key}") String stripeKey){

    }

}
