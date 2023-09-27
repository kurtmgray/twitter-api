package com.cooksys.spring_assessment.services;

import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;

public interface TweetService {
    TweetResponseDto createSimpleTweet(TweetRequestDto tweetRequestDto);
}
