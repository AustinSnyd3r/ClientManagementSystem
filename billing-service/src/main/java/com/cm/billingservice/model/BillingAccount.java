package com.cm.billingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
public class BillingAccount {
    @Id
    @GeneratedValue
    private UUID id;

    @Email(message = "Email is required. ")
    @Column(unique = true)
    private String email;

    private String stripeCustomerId;  /** This is for the clients to pay **/

    private String stripeConnectAccountId; /** This is for the coaches to get paid **/

    @NotNull
    private Boolean isActiveForPayment;
}
