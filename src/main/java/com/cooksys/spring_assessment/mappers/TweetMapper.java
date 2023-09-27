package com.cooksys.spring_assessment.mappers;

import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.entities.Tweet;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TweetMapper {

    Tweet dtoToEntity(TweetRequestDto tweetRequestDto);

}
