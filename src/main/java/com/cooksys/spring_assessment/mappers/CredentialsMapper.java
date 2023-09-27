package com.cooksys.spring_assessment.mappers;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.entities.Credentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    Credentials dtoToEntity(CredentialsDto credentials);
}
