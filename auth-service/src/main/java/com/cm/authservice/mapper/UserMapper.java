package com.cm.authservice.mapper;

import com.cm.authservice.dto.UserRequestDto;
import com.cm.authservice.dto.UserResponseDto;
import com.cm.authservice.model.User;

public class UserMapper {

    public User fromDto(UserRequestDto userRequestDto){
        User user = new User();
        user.setPassword(userRequestDto.getPassword());
        user.setEmail(userRequestDto.getEmail());

        return user;
    }

    public UserResponseDto toDto(User user){
        return new UserResponseDto(user.getEmail());
    }
}
