package com.cooksys.spring_assessment.mappers;

import com.cooksys.spring_assessment.dtos.HashtagDto;
import com.cooksys.spring_assessment.entities.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    List<HashtagDto> entitiesToDtos(List<Hashtag> tweetHashtags);
}
