package com.cm.authservice.service;

import com.cm.authservice.dto.EmailChangeRequestDTO;
import com.cm.authservice.dto.EmailChangeResponseDTO;
import com.cm.authservice.dto.LoginRequestDTO;
import com.cm.authservice.exception.TokenEmailDoesNotMatchException;
import com.cm.authservice.exception.UserNotFoundException;
import com.cm.authservice.model.User;
import com.cm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final static Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO){
        log.info("Authenticating a user: {}", loginRequestDTO.getEmail());

        // If user password matches, map the user to transform it into a token, which gets assigned to the optional.
        Optional<String> token = userService
                .findByEmail(loginRequestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(),
                    u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getId(), u.getRole()));

        return token;
    }

    public boolean validateToken(String token){
        try{
            jwtUtil.validateToken(token);
            return true;
        }
        catch (JwtException e){
            return false;
        }
    }

    public EmailChangeResponseDTO updateEmail(String token, EmailChangeRequestDTO emailChangeRequestDTO) {
        // Validate token and check if it belongs to same person.

        User user = userService.findById(jwtUtil.getIdFromToken(token))
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        if(!user.getEmail().equalsIgnoreCase(emailChangeRequestDTO.getOldEmail())){
            throw new TokenEmailDoesNotMatchException("Current account email does not match given old email.");
        }

        return userService.updateEmail(user, emailChangeRequestDTO);
    }

    public User getUser(String token) {
        UUID authUserId = jwtUtil.getIdFromToken(token);

        return userService.findById(authUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }
}
