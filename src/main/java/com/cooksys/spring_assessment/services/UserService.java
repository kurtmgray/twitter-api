package com.cooksys.spring_assessment.services;

import java.util.List;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.dtos.UserRequestDto;
import com.cooksys.spring_assessment.dtos.UserResponseDto;
import com.cooksys.spring_assessment.entities.User;

public interface UserService {
	
    User validateUser(CredentialsDto credentialsDto);

    UserResponseDto[] getAllUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto getSpecificUser(String username);

    UserResponseDto updateUser(UserRequestDto userRequestDto, String username);

    UserResponseDto deleteUser(CredentialsDto credentialsDto, String username);

    void follow(CredentialsDto credentialsDto, String username);

    void unfollow(CredentialsDto credentialsDto, String username);

    List<TweetResponseDto> getFeed(String username);

    List<TweetResponseDto> getTweets(String username);

    List<TweetResponseDto> getMentions(String username);

    List<UserResponseDto> getFollowers(String username);

    List<UserResponseDto> getFollowing(String username);

    
}