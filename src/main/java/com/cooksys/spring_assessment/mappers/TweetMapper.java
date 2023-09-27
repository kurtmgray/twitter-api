package com.cooksys.spring_assessment.mappers;

import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TweetMapper {

    Tweet dtoToEntity(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);
}
