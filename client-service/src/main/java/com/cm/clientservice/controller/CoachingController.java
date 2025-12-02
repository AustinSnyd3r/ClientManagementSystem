package com.cm.clientservice.controller;

import com.cm.clientservice.dto.UserRequestDTO;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.dto.contract.AgreementTemplateDto;
import com.cm.clientservice.dto.scheduling.TrainingScheduleDto;
import com.cm.clientservice.model.contract.AgreementTemplate;
import com.cm.clientservice.model.contract.CoachClientAgreement;
import com.cm.clientservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/coaching")
@Tag(name = "coaching", description = "API for coaching management.")
public class CoachingController {

    private final UserService userService;

    public CoachingController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/clients")
    @Operation(summary= "")
    public ResponseEntity<List<UserResponseDTO>> getClients(@RequestHeader("X-AUTH-ID") String authId){

        List<UserResponseDTO> clients = userService.getClientsOfCoach(UUID.fromString(authId));
        return ResponseEntity.ok().body(clients);
    }

    @GetMapping("/client-schedule/{clientId}")
    @Operation(summary = "Get the clients training schedule for the coach to view. ")
    public ResponseEntity<TrainingScheduleDto> getClientSchedule(@RequestHeader("X-AUTH-ID") String authId,
                                                                 @PathVariable String clientId){

        TrainingScheduleDto trainingScheduleDto =
                userService.getClientSchedule(UUID.fromString(clientId), UUID.fromString(authId));

        return ResponseEntity.ok().body(trainingScheduleDto);
    }

    @PostMapping("/propose-agreement/{clientId}")
    public ResponseEntity<UserResponseDTO> proposeClientAgreement(@RequestHeader("X-AUTH-ID") String authId,
                                                     @PathVariable String clientId,
                                                     @RequestBody CoachClientAgreement coachClientAgreement){

        // This endpoint will allow for a coach to propose a client agreement to a user.


        return null;
    }

    @PostMapping("/break-agreement")
    public ResponseEntity<UserResponseDTO> breakClientAgreement(@RequestHeader("X-AUTH-ID") String authId,
                                                      @RequestBody UserRequestDTO userRequestDTO,
                                                      @RequestBody CoachClientAgreement coachClientAgreement){
        // Alert the user they have been dropped.
        // Alert billing service that the contract is being terminated.
        return null;
    }


    // These should probably go in their own controller.
    //TODO: This should be accessed only by accounts that have a coaching profile.
    public ResponseEntity<AgreementTemplateDto> createAgreementTemplate(){
        return null;
    }

    // TODO: This should be accessed only by accounts that have a coaching profile
    public ResponseEntity<AgreementTemplateDto> deleteAgreementTemplate(){
        return null;
    }

    // TODO: This should be accessed only by accounts that have a coach prifkle.
    public ResponseEntity<AgreementTemplate> updateAgreementTemplate(){
        return null;
    }

    // TODO: This should be accessed only by accounts that have a coach profile.
    public ResponseEntity<List<AgreementTemplateDto>> getCoachAgreementTemplates(){
        return null;
    }
}
