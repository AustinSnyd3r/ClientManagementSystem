package com.cm.clientservice.service;

import com.cm.clientservice.dto.OutgoingEmailUpdateDTO;
import com.cm.clientservice.dto.UserRequestDTO;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.dto.scheduling.TrainingScheduleDto;
import com.cm.clientservice.exception.EmailAlreadyExistsException;
import com.cm.clientservice.exception.UnauthorizedScheduleAccessException;
import com.cm.clientservice.exception.UserNotFoundException;
import com.cm.clientservice.mapper.TrainingScheduleMapper;
import com.cm.clientservice.mapper.UserMapper;
import com.cm.clientservice.model.schedule.TrainingSchedule;
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
    private final CoachClientAgreementService coachClientAgreementService;
    private final TrainingScheduleService trainingScheduleService;
    private final WebClient webClient;

    public UserService(UserRepository userRepository,
                       WebClient.Builder webClientBuilder,
                       @Value("${auth.service.url}") String authServiceUrl,
                       CoachClientAgreementService coachClientAgreementService,
                       TrainingScheduleService trainingScheduleService){

        this.userRepository = userRepository;
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
        this.coachClientAgreementService = coachClientAgreementService;
        this.trainingScheduleService = trainingScheduleService;

    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO, String auth_id){
        // Make a call to the repository to create a user.
        if(userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A user with this email already exists"
                + userRequestDTO.getEmail());
        }

        User newUser = UserMapper.toModel(userRequestDTO);
        newUser.setAuthId(UUID.fromString(auth_id));
        newUser = userRepository.save(newUser);

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

            // Let auth service update their email for signing in.
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

    public List<UserResponseDTO> getClientsOfCoach(UUID coachAuthId){
        UUID coachId = userRepository.findByAuthId(coachAuthId).orElseThrow(
                () -> new UserNotFoundException("Coach was not found with authId: " + coachAuthId)).getId();

        return coachClientAgreementService
                .getCoachClientAgreements(coachId)
                .stream()
                .map(a -> UserMapper.toDTO(a.getClient()))
                .distinct()
                .toList();
    }

    public List<UserResponseDTO> getCoachesOfClient(UUID clientAuthId) {
        UUID clientId = userRepository.findByAuthId(clientAuthId).orElseThrow(
                () -> new UserNotFoundException("Client was not found with authId: " + clientAuthId)).getId();

        return coachClientAgreementService
                .getCoachClientAgreements(clientId)
                .stream()
                .map(a -> UserMapper.toDTO(a.getCoach()))
                .distinct()
                .toList();
    }

    // TODO: Fix this, right now it is using the authId of the coach.
    public TrainingScheduleDto getClientSchedule(UUID clientId, UUID coachAuthId){
        UUID coachId = userRepository.findByAuthId(coachAuthId).orElseThrow(
                                () -> new UserNotFoundException("Coach was not found with authId: " + coachAuthId))
                            .getId();

        if (!coachClientAgreementService.isUserAClientOfCoach(clientId, coachId)) {
            throw new UnauthorizedScheduleAccessException("The user with id: " + coachId + " is not a coach of user with id: " + clientId);
        }

        TrainingSchedule schedule = userRepository.findById(clientId).orElseThrow(
                () -> new UserNotFoundException("Client was not found with ID: " + clientId)).getTrainingSchedule();

        return TrainingScheduleMapper.toDto(schedule);
    }

    public TrainingScheduleDto getTrainingSchedule(UUID authId){

        TrainingSchedule schedule =
                userRepository.findByAuthId(authId)
                        .orElseThrow(
                                () -> new UserNotFoundException("User not found with authID: " + authId))
                .getTrainingSchedule();

        return TrainingScheduleMapper.toDto(schedule);
    }
}
