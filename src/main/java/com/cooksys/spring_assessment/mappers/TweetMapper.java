package com.cooksys.spring_assessment.mappers;

import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {

    Tweet dtoToEntity(TweetRequestDto tweetRequestDto, User user);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);

    TweetResponseDto entityToDto(Tweet tweet);
}
