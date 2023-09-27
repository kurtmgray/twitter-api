package com.cooksys.spring_assessment.services;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.dtos.HashtagDto;
import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Hashtag;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface TweetService {
    TweetResponseDto createSimpleTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweet(Long id);

    TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);

    TweetResponseDto addTweetReply(Long id, TweetRequestDto tweetRequestDto);

    void addLikeToTweet(Long id, CredentialsDto credentialsDto);

    List<HashtagDto> getAllTagsFromTweet(Long id);
}
