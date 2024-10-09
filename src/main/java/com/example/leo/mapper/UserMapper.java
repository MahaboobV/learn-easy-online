package com.example.leo.mapper;

import com.example.leo.entity.User;
import com.example.leo.model.UserRegistrationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegistrationDto userRegistrationDTO);

    UserRegistrationDto toDTO(User user);
}
