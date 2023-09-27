package com.cooksys.spring_assessment.mappers;

import com.cooksys.spring_assessment.dtos.UserResponseDto;
import com.cooksys.spring_assessment.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })
public interface UserMapper {

    @Mapping(source = "credentials.username", target = "username")
    @Mapping(source = "profile.email", target = "email")
    UserResponseDto userToUserResponseDto(User user);
}
