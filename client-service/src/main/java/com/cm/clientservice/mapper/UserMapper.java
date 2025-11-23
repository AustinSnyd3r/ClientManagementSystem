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

    public static User toModel(UserRequestDTO userRequestDTO){
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setAddress(userRequestDTO.getAddress());
        user.setEmail(userRequestDTO.getEmail());
        user.setDateOfBirth(LocalDate.parse(userRequestDTO.getDateOfBirth()));
        user.setRegistrationDate(LocalDate.parse(userRequestDTO.getRegisteredDate()));

        return user;
    }
}
