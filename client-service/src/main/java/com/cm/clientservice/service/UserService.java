package com.cm.clientservice.service;

import com.cm.clientservice.dto.OutgoingEmailUpdateDTO;
import com.cm.clientservice.dto.UserRequestDTO;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.exception.EmailAlreadyExistsException;
import com.cm.clientservice.exception.UserNotFoundException;
import com.cm.clientservice.mapper.UserMapper;
import com.cm.clientservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import com.cm.clientservice.model.User;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WebClient webClient;

    public UserService(UserRepository userRepository,
                       WebClient.Builder webClientBuilder,
                       @Value("${auth.service.url}") String authServiceUrl){

        this.userRepository = userRepository;
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();

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

    public List<UserResponseDTO> getAllCoachUsers(){
        List<User> users = userRepository.findByCoachProfileIsNotNull();

        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserResponseDTO updateUser(UUID authId, UserRequestDTO userRequestDTO, String token){
        // Find the user that has the authId
        User user = userRepository.findByAuthId(authId).orElseThrow(
            () ->  new UserNotFoundException("User not found with authId: " + authId));

        boolean updatingEmail = !user.getEmail().equalsIgnoreCase(userRequestDTO.getEmail());

        // Check if user is attempting to change their email to something someone else is using.
        if(updatingEmail && userRepository.existsByEmailAndIdNot(userRequestDTO.getEmail(), user.getId())){
            throw new EmailAlreadyExistsException("A user with this email already exists: "
                    + userRequestDTO.getEmail());
        }

        // We must ensure one of two things happens: BOTH or NEITHER work.
            // Attempt to change email in auth service.
            // Attempt to change user here.

        if(updatingEmail) {
            OutgoingEmailUpdateDTO outgoingEmailUpdateDTO = new OutgoingEmailUpdateDTO();
            outgoingEmailUpdateDTO.setOldEmail(user.getEmail());
            outgoingEmailUpdateDTO.setNewEmail(userRequestDTO.getEmail());

            // Make a request to the auth service to update the email of the user with this email.
            // Now check if the token they supplied was valid using our endpoint of auth service.
            webClient.put()
                    .uri("/auth/update-email")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .bodyValue(outgoingEmailUpdateDTO)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }

        user.setEmail(userRequestDTO.getEmail());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setAddress(userRequestDTO.getAddress());
        user.setDateOfBirth(LocalDate.parse(userRequestDTO.getDateOfBirth()));
        User updatedUser = userRepository.save(user);

        return UserMapper.toDTO(updatedUser);
    }


}
