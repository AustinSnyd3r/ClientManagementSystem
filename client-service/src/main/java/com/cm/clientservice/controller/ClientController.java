package com.cm.clientservice.controller;

import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final UserService userService;

    public ClientController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/coaches")
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserResponseDTO>> getAllCoachUsers(){
        List<UserResponseDTO> users = userService.getAllCoachUsers();
        return ResponseEntity.ok().body(users);
    }
}
