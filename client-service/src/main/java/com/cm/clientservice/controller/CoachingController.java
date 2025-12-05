package com.cm.clientservice.controller;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.dto.contract.template.AgreementTemplateRequestDto;
import com.cm.clientservice.dto.contract.template.AgreementTemplateResponseDto;
import com.cm.clientservice.dto.contract.CoachClientAgreementRequestDto;
import com.cm.clientservice.dto.contract.CoachClientAgreementResponseDto;
import com.cm.clientservice.dto.scheduling.TrainingScheduleDto;
import com.cm.clientservice.dto.scheduling.workout.WorkoutRequestDto;
import com.cm.clientservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("clients/coaching")
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
        //TODO: Alert user of withdrawal.
        //TODO: Alert billing service of end of contract.

        CoachClientAgreementResponseDto dto =
                userService.withdrawCoachesAgreementAcceptance(UUID.fromString(coachAuthId), UUID.fromString(agreementId));

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/templates")
    public ResponseEntity<AgreementTemplateResponseDto> createAgreementTemplate(@RequestHeader("X-AUTH-ID") String coachAuthId,
                                                                                @RequestBody AgreementTemplateRequestDto templateRequestDto){
        AgreementTemplateResponseDto dto =
                userService.createAgreementTemplate(UUID.fromString(coachAuthId), templateRequestDto);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/templates")
    public ResponseEntity<List<AgreementTemplateResponseDto>> getCoachesAgreementTemplates(@RequestHeader("X-AUTH-ID") String coachAuthId){
         List<AgreementTemplateResponseDto> dtoList =
                 userService.getCoachAgreementTemplates(UUID.fromString(coachAuthId));

         return ResponseEntity.ok().body(dtoList);
    }
}
