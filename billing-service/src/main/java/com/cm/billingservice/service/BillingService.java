package com.cm.billingservice.service;

import com.cm.billingservice.repository.BillingAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BillingService {

    private WebClient webClient;
    private BillingAccountRepository billingAccountRepository;



}
