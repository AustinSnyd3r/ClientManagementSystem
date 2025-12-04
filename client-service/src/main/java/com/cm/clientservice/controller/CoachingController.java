package com.cm.clientservice.controller;

import com.cm.clientservice.dto.UserRequestDTO;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.dto.contract.AgreementTemplateRequestDto;
import com.cm.clientservice.dto.contract.AgreementTemplateResponseDto;
import com.cm.clientservice.dto.contract.CoachClientAgreementRequestDto;
import com.cm.clientservice.dto.contract.CoachClientAgreementResponseDto;
import com.cm.clientservice.dto.scheduling.TrainingScheduleDto;
import com.cm.clientservice.dto.scheduling.workout.WorkoutRequestDto;
import com.cm.clientservice.model.contract.AgreementTemplate;
import com.cm.clientservice.model.contract.CoachClientAgreement;
import com.cm.clientservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("clients/coaching")
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

    @GetMapping("/clients/{clientId}/schedule")
    @Operation(summary = "Get the clients training schedule for the coach to view. ")
    public ResponseEntity<TrainingScheduleDto> getClientSchedule(@RequestHeader("X-AUTH-ID") String authId,
                                                                 @PathVariable String clientId){

        TrainingScheduleDto trainingScheduleDto =
                userService.getClientTrainingSchedule(UUID.fromString(clientId), UUID.fromString(authId));

        return ResponseEntity.ok().body(trainingScheduleDto);
    }

    @DeleteMapping("/clients/{clientId}/schedule/workout/{id}")
    public ResponseEntity<TrainingScheduleDto> deleteWorkoutFromClientSchedule(@RequestHeader("X-AUTH-ID") String authId,
                                                                         @PathVariable String clientId,
                                                                               @PathVariable String id){

        TrainingScheduleDto trainingScheduleDto =
                userService.removeWorkoutFromClientSchedule(UUID.fromString(authId), UUID.fromString(clientId), UUID.fromString(id));

        return ResponseEntity.ok().body(trainingScheduleDto);
    }

    @PostMapping("/clients/{clientId}/schedule/workout")
    public ResponseEntity<TrainingScheduleDto> addWorkoutToClientSchedule(@RequestHeader("X-AUTH-ID") String authId,
                                                                    @PathVariable String clientId,
                                                                    @RequestBody WorkoutRequestDto workoutDto){
        TrainingScheduleDto trainingScheduleDto =
                userService.addWorkoutToClientsSchedule(UUID.fromString(authId), UUID.fromString(clientId), workoutDto);

        return ResponseEntity.ok().body(trainingScheduleDto);
    }


    @PostMapping("/agreement/{clientId}")
    public ResponseEntity<CoachClientAgreementResponseDto> proposeClientAgreement(@RequestHeader("X-AUTH-ID") String coachAuthId,
                                                                                  @PathVariable String clientId,
                                                                                  @RequestBody CoachClientAgreementRequestDto coachClientAgreement){
        CoachClientAgreementResponseDto dto =
                userService.proposeCoachClientAgreement(UUID.fromString(coachAuthId),
                                                        UUID.fromString(clientId),
                                                        coachClientAgreement);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/agreement/withdraw/{agreementId}")
    public ResponseEntity<CoachClientAgreementResponseDto> withdrawCoachesAgreementAcceptance(@RequestHeader("X-AUTH-ID") String coachAuthId,
                                                      @PathVariable String agreementId){
        // Alert the user they have been dropped.
        // Alert billing service that the contract is being terminated.

        CoachClientAgreementResponseDto dto =
                userService.withdrawCoachesAgreementAcceptance(UUID.fromString(coachAuthId), UUID.fromString(agreementId));

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/templates")
    public ResponseEntity<AgreementTemplateResponseDto> createAgreementTemplate(@RequestHeader("X-AUTH-ID") String coachAuthId,
                                                                                @RequestBody AgreementTemplateRequestDto templateRequestDto){

        return ResponseEntity.ok().body(dto);
    }

    // TODO: This should be accessed only by accounts that have a coaching profile
    public ResponseEntity<AgreementTemplateResponseDto> deleteAgreementTemplate(){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/templates/template/{templateId}/update")
    public ResponseEntity<AgreementTemplateResponseDto> updateAgreementTemplate(@RequestHeader("X-AUTH-ID") String coachAuthId,
                                                                     @RequestBody AgreementTemplateRequestDto templateRequestDto,
                                                                     @PathVariable String templateId){
        AgreementTemplateResponseDto dto =
                //TODO: Add a validation group ot the request dto to make it optional for each of the fields.
                userService.updateAgreementTemplate(UUID.fromString(coachAuthId), UUID.fromString(templateId), templateRequestDto);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/templates")
    public ResponseEntity<List<AgreementTemplateResponseDto>> getCoachAgreementTemplates(@RequestHeader("X-AUTH-ID") String coachAuthId){
         List<AgreementTemplateResponseDto> dtoList =
                 userService.getCoachAgreementTemplates(UUID.fromString(coachAuthId));


         return ResponseEntity.ok().body(dtoList);
    }
}
