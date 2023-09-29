package com.cooksys.spring_assessment.controllers;

import com.cooksys.spring_assessment.dtos.*;
import com.cooksys.spring_assessment.services.TweetService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

  private final TweetService tweetService;

  @GetMapping
  public ResponseEntity<List<TweetResponseDto>> getAllTweets() {
    return new ResponseEntity<>(tweetService.getAllTweets(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<TweetResponseDto> createSimpleTweet(@RequestBody TweetRequestDto tweetRequestDto) {
    return new ResponseEntity<>(tweetService.createSimpleTweet(tweetRequestDto), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TweetResponseDto> getTweet(@PathVariable Long id) {
    return new ResponseEntity<>(tweetService.getTweet(id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<TweetResponseDto> deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
    return new ResponseEntity<>(tweetService.deleteTweet(id, credentialsDto), HttpStatus.OK);
  }

  @PostMapping("/{id}/like")
  public ResponseEntity<?> addLikeToTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
    tweetService.addLikeToTweet(id, credentialsDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/{id}/reply")
  public ResponseEntity<TweetResponseDto> addTweetReply(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
    return new ResponseEntity<>(tweetService.addTweetReply(id, tweetRequestDto), HttpStatus.CREATED);
  }

  @PostMapping("/{id}/repost")
  public ResponseEntity<TweetResponseDto> repostTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
    return new ResponseEntity<>(tweetService.repostTweet(id, credentialsDto), HttpStatus.CREATED);
  }


  @GetMapping("/{id}/tags")
  public ResponseEntity<List<HashtagDto>> getAllTagsFromTweet(@PathVariable Long id) {
    return new ResponseEntity<>(tweetService.getAllTagsFromTweet(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/likes")
  public ResponseEntity<List<UserResponseDto>> getAllUserLikesOfTweet(@PathVariable Long id) {
    return new ResponseEntity<>(tweetService.getAllUserLikesOfTweet(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/reposts")
  public ResponseEntity<List<TweetResponseDto>> getAllRepostsOfTweet(@PathVariable Long id) {
    return new ResponseEntity<>(tweetService.getAllRepostsOfTweet(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/mentions")
  public ResponseEntity<List<UserResponseDto>> getAllMentionsInTweet(@PathVariable Long id) {
    return new ResponseEntity<>(tweetService.getAllMentionsInTweet(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/replies")
  public ResponseEntity<List<TweetResponseDto>> getDirectRepliesToTweet(@PathVariable Long id) {
	  return new ResponseEntity<>(tweetService.getDirectRepliesToTweet(id), HttpStatus.OK);
  }
  
  @GetMapping("/{id}/context")
  public ResponseEntity<ContextDto> getContextToTweet(@PathVariable Long id) {
	  return new ResponseEntity<>(tweetService.getContextToTweet(id), HttpStatus.OK);
  }
  
}
