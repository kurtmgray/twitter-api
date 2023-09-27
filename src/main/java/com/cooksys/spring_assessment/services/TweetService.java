package com.cooksys.spring_assessment.services;

import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;

import java.util.List;

public interface TweetService {
    TweetResponseDto createSimpleTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> getAllTweets();
}
