package com.cm.clientservice.mapper;

import com.cm.clientservice.dto.UserRequestDTO;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.model.User;

import java.time.LocalDate;

public class UserMapper {
    public static UserResponseDTO toDTO(User patient){
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(patient.getId().toString());
        userDTO.setFirstName(patient.getFirstName());
        userDTO.setAddress(patient.getAddress());
        userDTO.setEmail(patient.getEmail());
        userDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return userDTO;
    }

    public static User toModel(UserRequestDTO patientRequestDTO){
        User user = new User();
        user.setFirstName(patientRequestDTO.getFirstName());
        user.setLastName(patientRequestDTO.getLastName());
        user.setAddress(patientRequestDTO.getAddress());
        user.setEmail(patientRequestDTO.getEmail());
        user.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        user.setRegistrationDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));

        return user;
    }
}
