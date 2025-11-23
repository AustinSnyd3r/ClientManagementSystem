package com.cm.authservice.controller;

import com.cm.authservice.dto.EmailChangeRequestDTO;
import com.cm.authservice.dto.EmailChangeResponseDTO;
import com.cm.authservice.dto.LoginRequestDTO;
import com.cm.authservice.dto.LoginResponseDTO;
import com.cm.authservice.model.User;
import com.cm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Generate token on user login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        if(tokenOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @GetMapping("/validate")
    @Operation(summary = "Validate token")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer "))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


        if(authService.validateToken(authHeader.substring(7))){
            User user = authService.getUser(authHeader.substring(7));

            return ResponseEntity.ok()
                    .header("X-AUTH-ID", user.getId()
                    .toString()).build();
        }

         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/update-email")
    @Operation(summary = "Update user account email.")
    public ResponseEntity<EmailChangeResponseDTO> updateEmail(@RequestHeader("Authorization") String authHeader,
                                                              @RequestBody EmailChangeRequestDTO emailChangeRequestDTO){

        if(authHeader == null || !authHeader.startsWith("Bearer "))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Now check if the token came from the person who claimed to want to change their email.
        EmailChangeResponseDTO emailChangeResponseDTO = authService
                .updateEmail(authHeader.substring(7), emailChangeRequestDTO);

        return ResponseEntity.ok().body(emailChangeResponseDTO);
    }
}
