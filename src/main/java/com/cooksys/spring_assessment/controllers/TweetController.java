package com.cooksys.spring_assessment.controllers;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.dtos.HashtagDto;
import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Hashtag;
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

  @PostMapping("/{id}/reply")
  public ResponseEntity<TweetResponseDto> addTweetReply(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
    return new ResponseEntity<>(tweetService.addTweetReply(id, tweetRequestDto), HttpStatus.CREATED);
  }

  @PostMapping("/{id}/like")
  public ResponseEntity<?> addLikeToTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
    tweetService.addLikeToTweet(id, credentialsDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/{id}/tags")
  public ResponseEntity<List<HashtagDto>> getAllTagsFromTweet(@PathVariable Long id) {
    return new ResponseEntity<>(tweetService.getAllTagsFromTweet(id), HttpStatus.OK);
  }
}
