package com.cm.clientservice.mapper;

import com.cm.clientservice.dto.UserRequestDTO;
import com.cm.clientservice.dto.UserResponseDTO;
import com.cm.clientservice.model.User;

import java.time.LocalDate;

public class UserMapper {
    public static UserResponseDTO toDTO(User user){
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId().toString());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAddress(user.getAddress());
        userDTO.setEmail(user.getEmail());
        userDTO.setDateOfBirth(user.getDateOfBirth().toString());

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
