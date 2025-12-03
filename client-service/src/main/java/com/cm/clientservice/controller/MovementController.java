package com.cm.clientservice.controller;
import com.cm.clientservice.dto.scheduling.movement.MovementCreateRequestDto;
import com.cm.clientservice.dto.scheduling.movement.MovementResponseDto;
import com.cm.clientservice.service.MovementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/clients/movements")
@Tag(name="movements", description = "API for management of movements. ")
public class MovementController {

    private final MovementService movementService;
    public MovementController(MovementService movementService){
        this.movementService = movementService;
    }

    @PostMapping
    public ResponseEntity<MovementResponseDto> createMovement(@RequestBody MovementCreateRequestDto movementCreateRequestDto){
        MovementResponseDto movementResponseDto =
                movementService.createMovement(movementCreateRequestDto);

        return ResponseEntity.ok().body(movementResponseDto);
    }

    @GetMapping("/id/{movementId}")
    public ResponseEntity<MovementResponseDto> getMovementById(@PathVariable String movementId){
        MovementResponseDto movementResponseDto =
                movementService.getMovementById(UUID.fromString(movementId));

        return ResponseEntity.ok().body(movementResponseDto);
    }
}
