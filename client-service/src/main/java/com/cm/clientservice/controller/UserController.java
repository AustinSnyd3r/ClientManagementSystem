package com.cm.clientservice.controller;

import com.cm.clientservice.dto.validators.CreateUserValidationGroup;
import com.cm.clientservice.service.UserService;
import jakarta.validation.groups.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.dto.UserRequestDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@Tag(name="Clients", description = "API for managing Clients")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a user.")
    public ResponseEntity<UserResponseDTO> createUser(
            @Validated({Default.class, CreateUserValidationGroup.class})
            @RequestBody UserRequestDTO userRequestDTO,
            @RequestHeader("X-AUTH-ID") String auth_id
    ){
        log.debug("AUTH ID IS {}", auth_id);
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO, auth_id);
        return ResponseEntity.ok().body(userResponseDTO);
    }


    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/")
    @Operation(summary = "Update a user")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestHeader("X-AUTH-ID") String id,
                                                      @RequestHeader("Authorization") String authHeader,
                                                       @RequestBody UserRequestDTO userRequestDTO
    ){
        int tokenStartIdx = 7;
        String token = authHeader.substring(tokenStartIdx);
        UUID authId = UUID.fromString(id);

        userService.updateUser(authId, userRequestDTO, token);

        return ResponseEntity.ok().body(new UserResponseDTO());
    }


    @GetMapping("/user-clients/{id}")
    @Operation(summary = "Get all the clients of a user.")
    public ResponseEntity<List<UserResponseDTO>> getAllUsersClients(@PathVariable UUID id){
        //TODO: Implement me and replace tis
        return ResponseEntity.ok().body(List.of(new UserResponseDTO()));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user.")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
