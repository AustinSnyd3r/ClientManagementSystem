package com.cm.clientservice.model.contract;

import com.cm.clientservice.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class CoachClientAgreement {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull(message = "Client profile must be included in coach-client agreement. ")
    private User client;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    @NotNull(message = "Coach profile must be included in coach-client agreement. ")
    private User coach;

    @ManyToOne
    @JoinColumn(name = "agreement_template_id")
    @NotNull(message = "Coach-Client Agreement must include an agreement template.")
    private AgreementTemplate agreementTemplate;

    @NotNull(message = "Coach-Client Agreement must have a start date.")
    private LocalDate startDate;

    @NotNull(message = "Client agreement status must be specified. ")
    private Boolean clientIsInAgreement;

    @NotNull(message = "Coach agreement status must be specified. ")
    private Boolean coachIsInAgreement;
}

