package com.cooksys.spring_assessment.services;

public interface TweetService {

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
