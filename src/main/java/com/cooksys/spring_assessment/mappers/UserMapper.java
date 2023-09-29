package com.cooksys.spring_assessment.mappers;

import com.cooksys.spring_assessment.dtos.UserResponseDto;
import com.cooksys.spring_assessment.dtos.UserRequestDto;
import com.cooksys.spring_assessment.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })
public interface UserMapper {

    @Mapping(source = "credentials.username", target = "username")
    UserResponseDto userToUserResponseDto(User user);

    List<UserResponseDto> entitiesToDtos(List<User> usersWhoLike);

    UserResponseDto[] entitiesToDtosA(User[] s);

    User requestToEntity(UserRequestDto userRequestDto);

}

