package com.cooksys.spring_assessment.mappers;

import com.cooksys.spring_assessment.entities.Profile;
import com.cooksys.spring_assessment.dtos.ProfileDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
	
	Profile dtoToEntity(ProfileDto dto);

}
