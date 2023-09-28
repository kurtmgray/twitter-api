package com.cooksys.spring_assessment.services.impl;

import com.cooksys.spring_assessment.services.HashtagService;
import com.cooksys.spring_assessment.repositories.HashtagRepository;
import com.cooksys.spring_assessment.mappers.HashtagMapper;
import com.cooksys.spring_assessment.dtos.HashtagDto;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

  private final HashtagRepository hashtagRepository;
  private final HashtagMapper hashtagMapper;
  
  @Override
  public List<HashtagDto> getAllHashtags() {
	  return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
  }
  
}