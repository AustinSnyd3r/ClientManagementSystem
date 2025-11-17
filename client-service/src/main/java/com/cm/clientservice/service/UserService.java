package com.cm.clientservice.service;

import com.cm.clientservice.dto.UserRequestDTO;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.exception.EmailAlreadyExistsException;
import com.cm.clientservice.mapper.UserMapper;
import com.cm.clientservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.cm.clientservice.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        // Make a call to the repository to create a user.
        if(userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A user with this email already exists"
                + userRequestDTO.getEmail());
        }

        User newUser = userRepository.save(
                UserMapper.toModel(userRequestDTO));

        return UserMapper.toDTO(newUser);
    }

    public void deleteUser(UUID id){
        userRepository.deleteById(id);
    }

    public List<UserResponseDTO> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserResponseDTO updateUserNOTDONE(UUID id, UserRequestDTO userRequestDTO){
        // Check if user is attempting to change their email to something someone else is using.

        // We must ensure one of two things happens: BOTH or NEITHER work.
            // Attempt to change email in auth service.
            // Attempt to change user here.
        return null;
    }


}
