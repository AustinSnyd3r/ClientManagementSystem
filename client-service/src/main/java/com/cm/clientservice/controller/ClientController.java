package com.cm.clientservice.controller;


/**
 * Spec:
 *
 * Coaches should be able to create a new Agreement Template for them to use
 *
 *
 *
 * Coaches should be able to propose a CoachClientAgreement to a user.
 *  - Require both parties to sign before continuing
 *  - Dependency on the BillingService to take billing info and schedule payments
 *
 * Coaches should be able to cancel a CoachClientAgreement
 *  - Notify the client of the cancelation
 *
 * Coaches should be able to propose edits to a CoachClientAgreement while running
 *  - Require both party signing to continue.
 *
 * Coaches should be able to
 *
 *
 *
 */

import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.dto.scheduling.TrainingScheduleDto;
import com.cm.clientservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@Tag(name="client", description = "API for clients to manage their experience.")
public class ClientController {

    private final UserService userService;

    public ClientController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/coaches")
    public ResponseEntity<List<UserResponseDTO>> getClientsCoaches(@RequestHeader("X-AUTH-ID") String authId){
        List<UserResponseDTO> coaches = userService.getCoachesOfClient(UUID.fromString(authId));
        return ResponseEntity.ok().body(coaches);
    }

    @GetMapping("/schedule")
    public ResponseEntity<TrainingScheduleDto> getTrainingSchedule(@RequestHeader("X-AUTH-ID") String authId){
        TrainingScheduleDto trainingScheduleDto = userService.getTrainingSchedule(UUID.fromString(authId));
        return ResponseEntity.ok().body(trainingScheduleDto);
    }
}
