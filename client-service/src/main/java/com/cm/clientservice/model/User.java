package com.cm.clientservice.model;

import com.cm.clientservice.model.contract.AgreementTemplate;
import com.cm.clientservice.model.contract.CoachClientAgreement;
import com.cm.clientservice.model.schedule.TrainingSchedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private UUID authId;

    @Email(message="Email is required")
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @NotNull
    private LocalDate registrationDate;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    private String address;

    @OneToMany(mappedBy = "coach")
    private List<CoachClientAgreement> agreementsAsCoach;

    @OneToMany(mappedBy = "client")
    private List<CoachClientAgreement> agreementsAsClient;

    @OneToMany(mappedBy = "author")
    private List<AgreementTemplate> authoredTemplates;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="training_schedule_id")
    private TrainingSchedule trainingSchedule;
}
