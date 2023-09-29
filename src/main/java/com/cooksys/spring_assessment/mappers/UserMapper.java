package com.cooksys.spring_assessment.mappers;

import com.cooksys.spring_assessment.dtos.UserRequestDto;
import com.cooksys.spring_assessment.dtos.UserResponseDto;
import com.cooksys.spring_assessment.entities.User;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })
public interface UserMapper {

    @Mapping(source = "credentials.username", target = "username")
    UserResponseDto uentityToDto(User test);
    

    UserResponseDto[] uentitiesToDtos(User[] s);

    List<UserResponseDto> uLEntitiesToDtos(List<User> s);


    User urequestToEntity(UserRequestDto userRequestDto);




    


}