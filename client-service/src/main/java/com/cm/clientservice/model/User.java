package com.cm.clientservice.model;

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

    @OneToOne
    @JoinColumn(name = "coach_profile_id")
    private CoachProfile coachProfile;

    @OneToOne
    @JoinColumn(name = "client_profile_id")
    private ClientProfile clientProfile;
}
