package com.cm.billingservice.controller;
import com.cm.billingservice.dto.AccountRegistrationRequestDto;
import com.cm.billingservice.dto.AccountRegistrationResponseDto;
import com.cm.billingservice.dto.activation.ActivateClientResponseDto;
import com.cm.billingservice.dto.activation.ActivateCoachRequestDto;
import com.cm.billingservice.dto.activation.ActivateCoachResponseDto;
import com.cm.billingservice.service.BillingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private BillingService billingService;

    @Value("${stripe.secret.key}")
    private String stripeKey;

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
    public ResponseEntity<AccountRegistrationResponseDto> registerAccount(
            @RequestBody AccountRegistrationRequestDto accountRegistrationRequestDto){

        AccountRegistrationResponseDto response = billingService.registerAccount(accountRegistrationRequestDto);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/coach/activate")
    public ResponseEntity<ActivateCoachResponseDto> activateCoach(
            @RequestBody ActivateCoachRequestDto activateCoachRequestDto){

        ActivateCoachResponseDto response = billingService.activateCoach(activateCoachRequestDto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/client/activate")
    public ResponseEntity<ActivateClientResponseDto> activateClient(){
        // Implement Me
        return ResponseEntity.ok().body(null);
    }
}
