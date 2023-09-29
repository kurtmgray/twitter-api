package com.cooksys.spring_assessment.controllers;


import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

 

  @GetMapping("/{id}/replies")
  public ResponseEntity<List<TweetResponseDto>> getDirectRepliesToTweet(@PathVariable Long id) {
	  return new ResponseEntity<>(tweetService.getDirectRepliesToTweet(id), HttpStatus.OK);
  }
  
  @GetMapping("/{id}/context")
  public ResponseEntity<ContextDto> getContextToTweet(@PathVariable Long id) {
	  return new ResponseEntity<>(tweetService.getContextToTweet(id), HttpStatus.OK);
  }
  
}
