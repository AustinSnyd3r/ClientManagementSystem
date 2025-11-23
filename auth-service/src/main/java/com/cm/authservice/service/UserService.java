package com.cm.authservice.service;
import com.cm.authservice.exception.EmailAlreadyExistsException;
import com.cm.authservice.exception.UserNotFoundException;

import com.cm.authservice.dto.EmailChangeRequestDTO;
import com.cm.authservice.dto.EmailChangeResponseDTO;
import com.cm.authservice.model.User;
import com.cm.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public EmailChangeResponseDTO updateEmail(EmailChangeRequestDTO emailChangeRequestDTO){

        // Don't change if the new email already exists
        if(userRepository.existsByEmail(emailChangeRequestDTO.getNewEmail())){
            throw new EmailAlreadyExistsException("User already exists with email: " + emailChangeRequestDTO.getNewEmail());
        }

        User user = userRepository.findByEmail(emailChangeRequestDTO.getOldEmail()).orElseThrow(
                () -> new UserNotFoundException("User was not found with email: " + emailChangeRequestDTO.getOldEmail()));

        user.setEmail(emailChangeRequestDTO.getNewEmail());
        User updatedUser = userRepository.save(user);

        EmailChangeResponseDTO emailChangeResponseDTO = new EmailChangeResponseDTO();
        emailChangeResponseDTO.setSavedEmail(updatedUser.getEmail());
        return emailChangeResponseDTO;
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }
}
