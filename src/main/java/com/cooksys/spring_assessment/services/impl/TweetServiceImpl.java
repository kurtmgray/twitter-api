package com.cooksys.spring_assessment.services.impl;

//import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.repositories.TweetRepository;
import com.cooksys.spring_assessment.services.TweetService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
	
	private final TweetRepository tweetRepository;
  //private final TweetMapper tweetMapper;
}
