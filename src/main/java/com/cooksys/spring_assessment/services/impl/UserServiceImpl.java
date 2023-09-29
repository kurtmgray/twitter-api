package com.cooksys.spring_assessment.services.impl;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.entities.Credentials;
import com.cooksys.spring_assessment.entities.User;
import com.cooksys.spring_assessment.exceptions.BadRequestException;
import com.cooksys.spring_assessment.exceptions.NotAuthorizedException;
import com.cooksys.spring_assessment.mappers.CredentialsMapper;
import com.cooksys.spring_assessment.repositories.UserRepository;
import com.cooksys.spring_assessment.services.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CredentialsMapper credentialsMapper;

    public User validateUser(CredentialsDto credentialsDto) throws BadRequestException, NotAuthorizedException {
        if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
            throw new BadRequestException("Both username and password are required.");
        }
        Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndCredentialsPassword(credentials.getUsername(), credentials.getPassword());

        if (optionalUser.isEmpty()) {
            throw new NotAuthorizedException("Invalid credentials.");
        }

        return optionalUser.get();
    }
}
