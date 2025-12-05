package com.cm.clientservice.controller;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.dto.contract.CoachClientAgreementResponseDto;
import com.cm.clientservice.dto.contract.ContractAgreementStatusRequestDto;
import com.cm.clientservice.dto.scheduling.TrainingScheduleDto;
import com.cm.clientservice.dto.scheduling.workout.WorkoutRequestDto;
import com.cm.clientservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("clients/client")
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

    @DeleteMapping("/schedule/workout/{workoutId}")
    public ResponseEntity<TrainingScheduleDto> deleteWorkoutFromSchedule(@RequestHeader("X-AUTH-ID") String authId,
                                                          @PathVariable String workoutId){

        TrainingScheduleDto trainingScheduleDto =
                userService.removeWorkoutFromSchedule(UUID.fromString(authId), UUID.fromString(workoutId));

        return ResponseEntity.ok().body(trainingScheduleDto);
    }

    @PostMapping("/schedule/workout")
    public ResponseEntity<TrainingScheduleDto> addWorkoutToSchedule(@RequestHeader("X-AUTH-ID") String authId,
                                                                    @RequestBody WorkoutRequestDto workoutDto){
        TrainingScheduleDto trainingScheduleDto =
                userService.addWorkoutToSchedule(UUID.fromString(authId), workoutDto);

        return ResponseEntity.ok().body(trainingScheduleDto);
    }

    @GetMapping("/agreements/pending")
    public ResponseEntity<List<CoachClientAgreementResponseDto>> getPendingAgreements(@RequestHeader("X-AUTH-ID") String authId){
        List<CoachClientAgreementResponseDto> dtos =
                userService.getPendingAgreements(UUID.fromString(authId));

        return ResponseEntity.ok().body(dtos);
    }

    @PutMapping("/agreements/{id}/status")
    public ResponseEntity<CoachClientAgreementResponseDto> setAcceptanceStatusOfPendingAgreement(@RequestHeader("X-AUTH-ID") String authId,
                                                                                                 @RequestBody ContractAgreementStatusRequestDto requestDto,
                                                                                                 @PathVariable String id){
        CoachClientAgreementResponseDto responseDto =
                userService.setAcceptanceStatusOfPendingAgreement(UUID.fromString(authId), UUID.fromString(id), requestDto);

        return ResponseEntity.ok().body(responseDto);
    }
}
