package com.cm.authservice.service;
import com.cm.authservice.dto.UserRequestDto;
import com.cm.authservice.dto.UserResponseDto;
import com.cm.authservice.exception.EmailAlreadyExistsException;

import com.cm.authservice.dto.EmailChangeRequestDTO;
import com.cm.authservice.dto.EmailChangeResponseDTO;
import com.cm.authservice.model.User;
import com.cm.authservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
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

    public UserResponseDto updateEmail(User user, UserRequestDto userRequestDto){

        if(userRepository.existsByEmail(userRequestDto.getEmail())){
            throw new EmailAlreadyExistsException("User already exists with email: " + userRequestDto.getEmail());
        }

        user.setEmail(userRequestDto.getEmail());
        User updatedUser = userRepository.save(user);

        return new UserResponseDto(updatedUser.getEmail());
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<UserResponseDto> registerUser(String email, String passwordHash) {
        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("This email is already taken: " + email);
        }

        User newUser = userRepository.save()

    }
}
