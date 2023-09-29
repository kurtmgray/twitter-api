package com.cooksys.spring_assessment.services;

import com.cooksys.spring_assessment.dtos.*;
import com.cooksys.spring_assessment.entities.Hashtag;
import com.cooksys.spring_assessment.entities.User;
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

    List<UserResponseDto> getAllUserLikesOfTweet(Long id);

    List<TweetResponseDto> getAllRepostsOfTweet(Long id);

    List<UserResponseDto> getAllMentionsInTweet(Long id);

    TweetResponseDto repostTweet(Long id, CredentialsDto credentialsDto);
    
    List<TweetResponseDto> getDirectRepliesToTweet(Long id);
    
    ContextDto getContextToTweet(Long id);
}
