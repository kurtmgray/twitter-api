package com.cooksys.spring_assessment.controllers;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
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
  public ResponseEntity<TweetResponseDto> getTweetById(@PathVariable Long id) {
    return new ResponseEntity<>(tweetService.getTweetById(id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<TweetResponseDto> deleteTweetById(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
    return new ResponseEntity<>(tweetService.deleteTweetById(id, credentialsDto), HttpStatus.OK);
  }

}
