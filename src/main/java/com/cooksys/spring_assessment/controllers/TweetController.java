package com.cooksys.spring_assessment.controllers;

import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.services.TweetService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

  private final TweetService tweetService;

  @PostMapping
  public ResponseEntity<TweetResponseDto> createSimpleTweet(@RequestBody TweetRequestDto tweetRequestDto) {
    return new ResponseEntity<>(tweetService.createSimpleTweet(tweetRequestDto), HttpStatus.CREATED);
  }


}
