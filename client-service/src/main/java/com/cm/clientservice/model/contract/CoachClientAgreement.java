package com.cm.clientservice.model.contract;

import com.cm.clientservice.model.ClientProfile;
import com.cm.clientservice.model.CoachProfile;
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
    /**
        Represents a running agreement document between
     */

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_profile_id")
    @NotNull(message = "Client profile must be included in coach-client agreement. ")
    private ClientProfile clientProfile;

    @ManyToOne
    @JoinColumn(name = "coach_profile_id")
    @NotNull(message = "Coach profile must be included in coach-client agreement. ")
    private CoachProfile coachProfile;

    @ManyToOne
    @JoinColumn(name = "agreement_template_id")
    @NotNull(message = "Coach-Client Agreement must include an agreement template.")
    private AgreementTemplate agreementTemplate;

    @NotNull(message = "Coach-Client Agreement must have a start date.")
    private LocalDate startDate;
}

