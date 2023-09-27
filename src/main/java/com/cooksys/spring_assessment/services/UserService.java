package com.cooksys.spring_assessment.services;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.entities.Credentials;
import com.cooksys.spring_assessment.entities.User;
import com.cooksys.spring_assessment.exceptions.BadRequestException;
import com.cooksys.spring_assessment.mappers.CredentialsMapper;

public interface UserService {

    User validateUser(CredentialsDto credentialsDto);

}
