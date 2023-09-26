package com.cooksys.spring_assessment.controllers;

import com.cooksys.spring_assessment.services.TweetService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweet")
public class TweetController {

  private final TweetService tweetService;

}
