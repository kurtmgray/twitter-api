package com.cooksys.spring_assessment.services.impl;

import com.cooksys.spring_assessment.services.*;
//import com.cooksys.spring_assessment.repositories.HashtagRepository;
import com.cooksys.spring_assessment.mappers.HashtagMapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

//  private final HashtagRepository hashtagRepository;
  private final HashtagMapper hashtagMapper;
  
}